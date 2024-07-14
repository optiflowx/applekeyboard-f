package com.optiflowx.optikeysx.ime.managers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.optiflowx.optikeysx.core.data.InstalledModelReference
import com.optiflowx.optikeysx.ime.recognizers.RecognizerSource
import com.optiflowx.optikeysx.ime.recognizers.providers.Providers
import com.optiflowx.optikeysx.ime.services.IMEService
import com.optiflowx.optikeysx.ime.services.MySpeechService
import com.optiflowx.optikeysx.optikeysxPreferences
import org.vosk.android.RecognitionListener
import java.io.IOException
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ModelManager(
    private val context: Context,
    private val listener: Listener
) {
    private val prefs by optikeysxPreferences()

    var isRunning = false
        private set

    val isPaused: Boolean
        get() = pausedState && speechService != null

    private var pausedState = false
    private var speechService: MySpeechService? = null
    private var recognizerSourceProviders = Providers(context)
    private var recognizerSourceModels: List<InstalledModelReference> = listOf()
    private var recognizerSources: MutableList<RecognizerSource> = ArrayList()
    private var currentRecognizerSourceIndex = 0
    private var currentRecognizerSource: RecognizerSource? = null
    private val executor: Executor = Executors.newSingleThreadExecutor()

    init {

        reloadModels()

//        //This will solve the recognizer being closed after the manager is closed!

    }

    private fun initializeRecognizer() {
        if (recognizerSources.size == 0) {
            return
        }
        currentRecognizerSource = recognizerSources[currentRecognizerSourceIndex]
        listener.onRecognizerSource(currentRecognizerSource!!)

        currentRecognizerSource!!.initialize(executor)
    }

    val currentRecognizerSourceAddSpaces: Boolean
        get() = currentRecognizerSource?.addSpaces ?: true

    fun initializeFirstLocale(): Boolean {
        if (recognizerSources.size == 0) {
            listener.onError(ErrorType.NO_RECOGNIZERS_INSTALLED)
            listener.onStateChanged(State.STATE_ERROR)
            return false
        }

        currentRecognizerSourceIndex = 0
        initializeRecognizer()
        return true
    }

    fun start(attributionContext: Context? = null) {
        if (currentRecognizerSource == null) {
            Log.w(TAG, "currentRecognizerSource is null!")
            return
        }

        if (currentRecognizerSource!!.closed) {
            Log.w(
                TAG,
                "Trying to start a closed Recognizer Source: ${currentRecognizerSource!!.name}"
            )
            return
        }

        if (isRunning || speechService != null) {
            speechService!!.stop()
        }

        isRunning = true
        listener.onStateChanged(State.STATE_LISTENING)
        try {
            val recognizer = currentRecognizerSource!!.recognizer
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) return


            speechService = MySpeechService(recognizer, recognizer.sampleRate, attributionContext)
            speechService!!.startListening(listener)
        } catch (e: IOException) {
            Toast.makeText(
                attributionContext,
                "Mic is already in use: More::${e.message}",
                Toast.LENGTH_LONG
            ).show()
            listener.onStateChanged(State.STATE_ERROR)
        }
    }

    fun reloadModels() {
        val newModels = prefs.modelsOrder.get()

        if (newModels == recognizerSourceModels)
            return

        recognizerSources.clear()
        recognizerSourceModels = newModels
        recognizerSourceModels.forEach { model ->
            recognizerSourceProviders.recognizerSourceForModel(model)?.let {
                recognizerSources.add(it)
            }
        }

        if (recognizerSources.size == 0) {
            listener.onError(ErrorType.NO_RECOGNIZERS_INSTALLED)
            listener.onStateChanged(State.STATE_ERROR)
        }
    }

    fun pause(checked: Boolean) {
        if (speechService != null) {
            speechService!!.setPause(checked)
            pausedState = checked
            if (checked) {
                listener.onStateChanged(State.STATE_PAUSED)
            } else {
                listener.onStateChanged(State.STATE_LISTENING)
            }
        } else {
            pausedState = false
        }
    }

    fun stop(forceFreeRam: Boolean = false) {
        prefs.recognitionState.set(IMEService.STATE_INITIAL)
        speechService?.let {
            executor.execute {
                it.stop()
                it.shutdown()
            }
        }
        speechService = null
        isRunning = false
        stopRecognizerSource(forceFreeRam || !prefs.keepLanguageModelInMemory.get())
    }

    private fun stopRecognizerSource(freeRam: Boolean) {
        currentRecognizerSource?.let {
            executor.execute {
                it.close(freeRam)
            }
        }
        listener.onStateChanged(State.STATE_STOPPED)
    }

    fun onDestroy() {
        stop(true)
    }

    companion object {
        private const val TAG = "ModelManager"
    }

    interface Listener : RecognitionListener {
        fun onStateChanged(state: State)

        fun onError(type: ErrorType)

        fun onRecognizerSource(source: RecognizerSource)
    }

    enum class State {
        STATE_LOADING, STATE_READY, STATE_LISTENING, STATE_PAUSED, STATE_ERROR, STATE_STOPPED
    }

    enum class ErrorType {
        NO_RECOGNIZERS_INSTALLED
    }
}