package com.optiflowx.optikeysx.ime

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.optiflowx.optikeysx.core.data.InstalledModelReference
import com.optiflowx.optikeysx.ime.recognizers.RecognizerSource
import com.optiflowx.optikeysx.ime.recognizers.providers.Providers
import com.optiflowx.optikeysx.optikeysxPreferences
import com.optiflowx.optikeysx.views.defaults.KeyboardViewManager
import java.io.IOException
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ModelManager(
    private val ime: IMEService,
    private val keyboardViewManager: KeyboardViewManager
) {
    private val prefs by optikeysxPreferences()
    
    private var speechService: MySpeechService? = null
    var isRunning = false
        private set

    val openSettingsOnMic: Boolean
        get() = recognizerSources.size == 0

    private var recognizerSourceProviders = Providers(ime)
    private var recognizerSourceModels: List<InstalledModelReference> = listOf()
    private var recognizerSources: MutableList<RecognizerSource> = ArrayList()
    private var currentRecognizerSourceIndex = 0
    private var currentRecognizerSource: RecognizerSource? = null
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private fun initializeRecognizer() {
        if (recognizerSources.size == 0) {
            return
        }
        val onLoaded = Observer { r: RecognizerSource? ->
            if (prefs.startRecognitionInstantaneously.get()) {
                start() // execute after initialize
            }
        }
        currentRecognizerSource = recognizerSources[currentRecognizerSourceIndex]
        keyboardViewManager.recognizerNameLD.postValue(currentRecognizerSource!!.name)
        currentRecognizerSource!!.stateLD.observe(ime.lifecycleOwner, keyboardViewManager)
        currentRecognizerSource!!.initialize(executor, onLoaded)
    }

    val currentRecognizerSourceAddSpaces: Boolean
        get() = currentRecognizerSource?.addSpaces ?: true

    fun switchToNextRecognizer() {
        if (recognizerSources.size == 0 || recognizerSources.size == 1) return
        stop(true)
        currentRecognizerSourceIndex++
        if (currentRecognizerSourceIndex >= recognizerSources.size) {
            currentRecognizerSourceIndex = 0
        }
        initializeRecognizer() // start is called after the recognizer is initialized
    }

    fun start() {
        if (currentRecognizerSource == null) {
            Log.w(
                TAG,
                "currentRecognizerSource is null!"
            )
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
        keyboardViewManager.stateLD.postValue(KeyboardViewManager.STATE_LISTENING)
        
        try {
            val recognizer = currentRecognizerSource!!.recognizer
            if (ActivityCompat.checkSelfPermission(
                    ime,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            speechService = MySpeechService(recognizer, recognizer.sampleRate)
            speechService!!.startListening(ime)
        } catch (e: IOException) {
            keyboardViewManager.errorMessageLD.postValue("Microphone error: " + e.message)
            keyboardViewManager.stateLD.postValue(KeyboardViewManager.STATE_ERROR)
        }
    }

    private var pausedState = false

    init {
        reloadModels()
    }

    private fun reloadModels() {
        val newModels = prefs.modelsOrder.get()
        if (newModels == recognizerSourceModels) {
            if (prefs.startRecognitionInstantaneously.get()) {
                if (currentRecognizerSource != null) {
                    start()
                }
            }
            return
        }

        recognizerSources.clear()
        recognizerSourceModels = newModels
        recognizerSourceModels.forEach { model ->
            recognizerSourceProviders.recognizerSourceForModel(model)?.let {
                recognizerSources.add(it)
            }
        }

        if (recognizerSources.size == 0) {
            keyboardViewManager.errorMessageLD.postValue("No models found. Please download a model from the settings.")
            keyboardViewManager.stateLD.postValue(KeyboardViewManager.STATE_ERROR)
        } else {
            currentRecognizerSourceIndex = 0
            initializeRecognizer()
        }
    }

    fun pause(checked: Boolean) {
        if (speechService != null) {
            speechService!!.setPause(checked)
            pausedState = checked
            if (checked) {
                keyboardViewManager.stateLD.postValue(KeyboardViewManager.STATE_PAUSED)
            } else {
                keyboardViewManager.stateLD.postValue(KeyboardViewManager.STATE_LISTENING)
            }
        } else {
            pausedState = false
        }
    }

    val isPaused: Boolean
        get() = pausedState && speechService != null

    fun stop(forceFreeRam: Boolean = false) {
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
        currentRecognizerSource?.stateLD?.removeObserver(keyboardViewManager)
    }

    fun onDestroy() {
        stop(true)
    }

    fun onResume() {
        reloadModels()
    }

    companion object {
        private const val TAG = "ModelManager"
    }
}
