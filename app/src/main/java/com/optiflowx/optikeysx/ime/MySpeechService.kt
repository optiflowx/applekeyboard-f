/*
 * org.vosk.SpeechService, extended to support other recognizers.
 */
package com.optiflowx.optikeysx.ime

import android.Manifest
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.optiflowx.optikeysx.ime.recognizers.Recognizer
import org.vosk.android.RecognitionListener
import java.io.IOException
import kotlin.math.abs


class MySpeechService @RequiresPermission(Manifest.permission.RECORD_AUDIO) constructor(
    private val recognizer: Recognizer, sampleRate: Float, val getAmplitudeData: (Int) -> Unit,
) {
    private val sampleRate: Int
    private val bufferSize: Int
    var recorder: AudioRecord
    private var recognizerThread: RecognizerThread? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        this.sampleRate = sampleRate.toInt()
        bufferSize = Math.round(this.sampleRate.toFloat() * BUFFER_SIZE_SECONDS)
        val audioSource = MediaRecorder.AudioSource.VOICE_RECOGNITION
        val channelConfig = AudioFormat.CHANNEL_IN_MONO
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        val bufferSizeInBytes = bufferSize * 2

        recorder = AudioRecord(
            audioSource,
            this.sampleRate,
            channelConfig,
            audioFormat,
            bufferSizeInBytes
        )

        if (recorder.state == 0) {
            recorder.release()
            throw IOException("Failed to initialize recorder. Microphone might be already in use.")
        }
    }

    fun startListening(listener: RecognitionListener): Boolean {
        return if (null != recognizerThread) {
            false
        } else {
            recognizerThread =
                RecognizerThread(listener)
            recognizerThread!!.start()
            true
        }
    }

    private fun stopRecognizerThread(): Boolean {
        return if (null == recognizerThread) {
            false
        } else {
            try {
                recognizerThread!!.interrupt()
                recognizerThread!!.join()
            } catch (var2: InterruptedException) {
                Thread.currentThread().interrupt()
            }
            recognizerThread = null
            true
        }
    }

    fun stop(): Boolean {
        return stopRecognizerThread()
    }

    fun cancel(): Boolean {
        if (recognizerThread != null) {
            recognizerThread!!.setPause(true)
        }
        return stopRecognizerThread()
    }

    fun shutdown() {
        recorder.release()
    }

    fun setPause(paused: Boolean) {
        if (recognizerThread != null) {
            recognizerThread!!.setPause(paused)
        }
    }

    fun reset() {
        if (recognizerThread != null) {
            recognizerThread!!.reset()
        }
    }

    private inner class RecognizerThread @JvmOverloads constructor(
        var listener: RecognitionListener,
        timeout: Int = -1
    ) : Thread() {
        private var remainingSamples: Int
        private val timeoutSamples: Int

        @Volatile
        private var paused = false

        @Volatile
        private var reset = false

        init {
            timeoutSamples = if (timeout != -1) {
                timeout * sampleRate / 1000
            } else {
                -1
            }
            remainingSamples = timeoutSamples
        }

        fun setPause(paused: Boolean) {
            this.paused = paused
        }

        fun reset() {
            reset = true
        }

        override fun run() {
            recorder.startRecording()
            if (recorder.recordingState == 1) {
                recorder.stop()
                val ioe =
                    IOException("Failed to start recording. Microphone might be already in use.")
                mainHandler.post { listener.onError(ioe) }
            }
            val buffer = ShortArray(bufferSize)
            while (!interrupted() && (timeoutSamples == -1 || remainingSamples > 0)) {
                // read the data into the buffer
                val read = recorder.read(buffer, 0, buffer.size)
//                val amplitude = buffer[0].toInt() and 0xff shl 8 or buffer[1].toInt()

//                val amplitudeDB = 20 * log10(abs(amplitude).toDouble() / 32768).toInt()

                var average = 0.0
                for (s in buffer) {
                    average += abs(s.toDouble())
                }

                val amps: Double = (average / buffer.size)

                if (!paused) {
                    if (reset) {
                        recognizer.reset()
                        reset = false
                    }

                    if (read < 0) {
                        throw RuntimeException("error reading audio buffer")
                    }

                    // Determine amplitude
                    getAmplitudeData(amps.toInt())

                    var result: String?
                    if (recognizer.acceptWaveForm(buffer, read)) {
                        result = recognizer.getResult()
                        mainHandler.post { listener.onResult(result) }
                    } else {
                        result = recognizer.getPartialResult()
                        mainHandler.post { listener.onPartialResult(result) }
                    }

                    if (timeoutSamples != NO_TIMEOUT) {
                        remainingSamples -= read
                    }
                }
            }

            recorder.stop()

            if (!paused) {
                if (timeoutSamples != NO_TIMEOUT && remainingSamples <= 0) {
                    mainHandler.post { listener.onTimeout() }
                } else {
                    val finalResult = recognizer.getFinalResult()
                    mainHandler.post { listener.onFinalResult(finalResult) }
                }
            }
        }
    }

    companion object {
        private const val NO_TIMEOUT = -1
        private const val BUFFER_SIZE_SECONDS = 0.2f
    }
}
