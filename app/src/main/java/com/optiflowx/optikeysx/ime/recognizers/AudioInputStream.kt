package com.optiflowx.optikeysx.ime.recognizers

import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.io.InputStream

class AudioInputStream @RequiresPermission(android.Manifest.permission.RECORD_AUDIO) constructor(
    context: Context,
    audioSource: Int = MediaRecorder.AudioSource.DEFAULT,
    sampleRate: Int = 44100,
    channelConfig: Int = AudioFormat.CHANNEL_IN_MONO,
    audioFormat: Int = AudioFormat.ENCODING_PCM_8BIT,
    bufferSizeInBytes: Int = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)) : InputStream() {

    private val audioRecord = if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.RECORD_AUDIO
        ) != PackageManager.PERMISSION_GRANTED
    ) {
         AudioRecord(audioSource, sampleRate, channelConfig, audioFormat, bufferSizeInBytes).apply {
            startRecording()
        }
    } else {
        throw SecurityException("RECORD_AUDIO permission is not granted")
    }

    @Deprecated("Use read(audioData, offset, length)")
    @Throws(IOException::class)
    override fun read(): Int {
        val tmp = byteArrayOf(0)
        read(tmp, 0, 1)
        return tmp[0].toInt()
    }

    @Throws(IOException::class)
    override fun read(audioData: ByteArray, offset: Int, length: Int): Int {
        try {
            return audioRecord.read(audioData, offset, length)
        } catch (e: Exception) {
            throw IOException(e)
        }
    }

    override fun close() {
        audioRecord.stop()
        audioRecord.release()
        super.close()
    }
}