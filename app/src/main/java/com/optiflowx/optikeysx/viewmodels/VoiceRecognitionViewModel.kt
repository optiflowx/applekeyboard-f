
package com.optiflowx.optikeysx.viewmodels

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optiflowx.optikeysx.core.data.InstalledModelReference
import com.optiflowx.optikeysx.core.enums.KeepScreenAwakeMode
import com.optiflowx.optikeysx.core.enums.Mode
import com.optiflowx.optikeysx.core.enums.RecognitionState
import com.optiflowx.optikeysx.core.enums.RecognizerState
import com.optiflowx.optikeysx.extension.getAudioPermission
import com.optiflowx.optikeysx.ime.IMEService
import com.optiflowx.optikeysx.ime.MySpeechService
import com.optiflowx.optikeysx.ime.recognizers.RecognizerSource
import com.optiflowx.optikeysx.ime.recognizers.providers.Providers
import com.optiflowx.optikeysx.optikeysxPreferences
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.vosk.android.RecognitionListener
import java.io.IOException
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@SuppressLint("StaticFieldLeak")
class VoiceRecognitionViewModel(private val ime: IMEService) : ViewModel(),
    Observer<RecognizerState>, RecognitionListener {

    private val prefs by optikeysxPreferences()

    private var speechService: MySpeechService? = null

    private var recognizerSourceProviders = Providers(ime)
    private var recognizerSourceModels: List<InstalledModelReference> = listOf()
    private var recognizerSources: MutableList<RecognizerSource> = ArrayList()
    var amplitudes: MutableStateFlow<PersistentList<Int>> = MutableStateFlow(persistentListOf())
    private var currentRecognizerSourceIndex = 0
    private var currentRecognizerSource: RecognizerSource? = null
    private val executor: Executor = Executors.newSingleThreadExecutor()

    private val recognizerNameLD = MutableLiveData("")
    val stateLD = MutableLiveData(RecognitionState.NO_MODEL)

    private var pausedState = false
    private var addSpace = false
    private var capitalize = true
    private var firstSinceResume = true
    private var composing = false
    private var isRunning = false

    private val isPaused: Boolean
        get() = pausedState && speechService != null



    val currentRecognizerSourceAddSpaces: Boolean
        get() = currentRecognizerSource?.addSpaces ?: true

    companion object {
        private const val TAG = "VoiceRecognitionViewModel"
        private val sentenceTerminator = charArrayOf('.', '\n', '!', '?')
    }

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
            stateLD.postValue(RecognitionState.NO_MODEL)
        } else {
            currentRecognizerSourceIndex = 0
            initializeRecognizer()
        }
    }

    fun onRecordHandler() {
        if (isRunning) {
            if (isPaused) {
                this.pause(false)
                if (prefs.keepScreenAwake.get() == KeepScreenAwakeMode.WHEN_LISTENING) {
                    ime.setKeepScreenOn(true)
                }
            } else {
                this.pause(true)
                if (prefs.keepScreenAwake.get() == KeepScreenAwakeMode.WHEN_LISTENING) {
                    ime.setKeepScreenOn(false)
                }
            }
        } else {
            this.start()
            if (prefs.keepScreenAwake.get() == KeepScreenAwakeMode.WHEN_LISTENING) {
                ime.setKeepScreenOn(true)
            }
        }
    }

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
        if (currentRecognizerSource == null || currentRecognizerSource!!.closed) {
            return
        }

        if (isRunning || speechService != null) {
            speechService!!.stop()
        }

        isRunning = true
        stateLD.postValue(RecognitionState.LISTENING)

        try {
            val recognizer = currentRecognizerSource!!.recognizer
            if (ActivityCompat.checkSelfPermission(
                    ime,
                    getAudioPermission()
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            speechService = MySpeechService(recognizer, recognizer.sampleRate) { data ->
                viewModelScope.launch(Dispatchers.IO) {
                    amplitudes.value = amplitudes.value.add(data)
                }
            }
            speechService!!.startListening(this)

        } catch (e: IOException) {
            stateLD.postValue(RecognitionState.NO_MIC_PERMISSION)
        }
    }

    fun pause(checked: Boolean) {
        if (speechService != null) {
            speechService!!.setPause(checked)

            pausedState = checked
            if (checked) {
                stateLD.postValue(RecognitionState.PAUSED)
            } else {
                stateLD.postValue(RecognitionState.LISTENING)
            }
        } else {
            pausedState = false
        }
    }

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

    fun resume() {
        firstSinceResume = true;
        reloadModels()
    }

    private fun onText(text: String, mode: Mode) {
        if (text.isEmpty())  // no need to commit empty text
            return

        if (firstSinceResume) {
            firstSinceResume = false
            checkAddSpaceAndCapitalize()
        }

        val ic = ime.currentInputConnection ?: return

        var spacedText = text
        if (prefs.isAutoCapitalisation.get() && capitalize) {
            spacedText = spacedText[0].uppercase() + spacedText.substring(1)
        }

        when (mode) {
            Mode.FINAL, Mode.STANDARD -> {
                // add a space next time. Usually overridden by onUpdateSelection
                addSpace = addSpaceAfter(
                    spacedText[spacedText.length - 1] // last char
                )
                capitalizeAfter(
                    spacedText
                )?.let {
                    capitalize = it
                }
                composing = false
                ic.commitText(spacedText, 1)
            }

            Mode.PARTIAL -> {
                composing = true
                ic.setComposingText(spacedText, 1)
            }

            Mode.INSERT -> {                // Manual insert. Don't add a space.
                composing = false
                ic.commitText(text, 1)
            }
        }
    }

    private fun onUpdateSelection(
        newSelStart: Int,
        newSelEnd: Int,
    ) {
        if (!composing) {
            if (newSelStart == newSelEnd) { // cursor moved
                checkAddSpaceAndCapitalize()
            }
        }
    }

    private fun checkAddSpaceAndCapitalize() {
        val cs = ime.currentInputConnection.getTextBeforeCursor(3, 0)
        if (cs != null) {
            addSpace = cs.isNotEmpty() && addSpaceAfter(cs[cs.length - 1])

            val value = capitalizeAfter(cs)
            value?.let {
                capitalize = it
            }
        }
    }

    private fun capitalizeAfter(string: CharSequence): Boolean? {
        for (char in string.reversed()) {
            if (char.isLetterOrDigit()) {
                return false
            }
            if (char in sentenceTerminator) {
                return true
            }
        }
        return null
    }

    private fun addSpaceAfter(char: Char): Boolean = when (char) {
        '"' -> false
        '*' -> false
        ' ' -> false
        '\n' -> false
        '\t' -> false
        else -> true
    }

    private fun initializeRecognizer() {
        if (recognizerSources.size == 0) {
            return
        }

        val onLoaded = Observer { _: RecognizerSource? ->
            if (prefs.startRecognitionInstantaneously.get()) {
                start() // execute after initialize
            }
        }

        currentRecognizerSource = recognizerSources[currentRecognizerSourceIndex]
        recognizerNameLD.postValue(currentRecognizerSource!!.name)
        currentRecognizerSource!!.stateLD.observe(ime.lifecycleOwner, this)
        currentRecognizerSource!!.initialize(executor, onLoaded)
    }

    private fun stopRecognizerSource(freeRam: Boolean) {
        currentRecognizerSource?.let {
            executor.execute {
                it.close(freeRam)
            }
        }
    }

    override fun onCleared() {
        stop(true)
        currentRecognizerSource?.stateLD?.removeObserver(this)
        super.onCleared()
    }

    override fun onResult(text: String) {
//        Log.d("VoskIME", "Result: $text")
        if (text.isEmpty()) return
        onText(text, Mode.STANDARD)
    }

    override fun onFinalResult(text: String) {
//        Log.d("VoskIME", "Final result: $text")
        if (text.isEmpty()) return
        onText(text, Mode.FINAL)
    }

    override fun onPartialResult(partialText: String) {
//        Log.d("VoskIME", "Partial result: $partialText")
        if (partialText == "") return

        onText(partialText, Mode.PARTIAL)
    }

    override fun onError(e: Exception) {
        stateLD.postValue(RecognitionState.RECOGNIZER_ERROR)
    }

    override fun onTimeout() {
        stateLD.postValue(RecognitionState.PAUSED)
    }

    override fun onChanged(value: RecognizerState) {
        when (value) {
            RecognizerState.CLOSED, RecognizerState.NONE -> stateLD.setValue(RecognitionState.INITIAL)
            RecognizerState.LOADING -> stateLD.setValue(RecognitionState.LOADING)
            RecognizerState.READY -> stateLD.setValue(RecognitionState.READY)
            RecognizerState.IN_RAM -> stateLD.setValue(RecognitionState.PAUSED)
            RecognizerState.ERROR -> stateLD.setValue(RecognitionState.RECOGNIZER_ERROR)
        }
    }
}
