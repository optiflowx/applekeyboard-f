package com.optiflowx.optikeysx.ime

import android.inputmethodservice.InputMethodService
import android.os.Build
import android.os.IBinder
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.optiflowx.optikeysx.core.data.KeyboardData
import com.optiflowx.optikeysx.core.enums.KeepScreenAwakeMode
import com.optiflowx.optikeysx.ime.recognizers.RecognizerSource
import com.optiflowx.optikeysx.optikeysxPreferences
import com.optiflowx.optikeysx.views.KeyboardViewManager
import org.vosk.BuildConfig
import org.vosk.LibVosk
import org.vosk.LogLevel

class IMEService : InputMethodService(), ModelManager.Listener {
    private val prefs by optikeysxPreferences()
    private val lifecycleOwner = IMELifecycleOwner()

    //Data members
    private lateinit var editorInfo: EditorInfo
    private lateinit var keyboardViewManager: KeyboardViewManager
    private lateinit var modelManager: ModelManager
    private lateinit var actionManager: ActionManager
    private lateinit var textManager: TextManager

    private var currentRecognizerSource: RecognizerSource? = null

    var keyboardData = KeyboardData()

    var isRichTextEditor = true
        private set

    private val token: IBinder?
        get() {
            val window = myWindow ?: return null
            return window.attributes.token
        }

    private val myWindow: Window?
        get() {
            return window.window
        }

    fun onRecognition() {
        if (prefs.isEnableSpeechRecognition.get() && prefs.modelsOrder.get().isNotEmpty()) {

            if (modelManager.isRunning) {
                if (modelManager.isPaused) {
                    Log.d("VoskIME", "Resuming")
                    modelManager.pause(false)
                    if (prefs.keepScreenAwake.get() == KeepScreenAwakeMode.WHEN_LISTENING)
                        setKeepScreenOn(true)
                } else {
                    Log.d("VoskIME", "Pausing")
                    modelManager.pause(true)
                    if (prefs.keepScreenAwake.get() == KeepScreenAwakeMode.WHEN_LISTENING)
                        setKeepScreenOn(false)
                }
            } else {
                Log.d("VoskIME", "Starting")
                modelManager.start()
                if (prefs.keepScreenAwake.get() == KeepScreenAwakeMode.WHEN_LISTENING)
                    setKeepScreenOn(true)
            }
        }
    }

    private fun findEnterAction(): Int {
        val action = editorInfo.imeOptions and EditorInfo.IME_MASK_ACTION
        if (editorInfo.imeOptions and EditorInfo.IME_FLAG_NO_ENTER_ACTION == 0 && action in editorActions) {
            return action
        }

        return EditorInfo.IME_ACTION_UNSPECIFIED
    }

    private fun setKeepScreenOn(keepScreenOn: Boolean) {
        val windowCompat = myWindow ?: return

        if (keepScreenOn) {
            windowCompat.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            windowCompat.clearFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
        }
    }

    private fun updateCapsLock() {
        if (prefs.isAutoCapitalisation.get()) {
            prefs.isAllCaps.set(true)
            prefs.isCapsLock.set(false)
        }
    }

    private fun startCapsHandler() {
        val ic = this.currentInputConnection
        if(ic != null) {
            val text = ic.getTextBeforeCursor(2, 0)
            if(text != null) {
                if(text.isEmpty() || text[0] == ' ') {
                    updateCapsLock()
                }
            } else {
                val capsMode = ic.getCursorCapsMode(editorInfo.inputType)
                val value = capsMode and InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS != 0
                prefs.isAllCaps.set(value)
                prefs.isCapsLock.set(value)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        lifecycleOwner.onCreate()

        LibVosk.setLogLevel(if (BuildConfig.DEBUG) LogLevel.INFO else LogLevel.WARNINGS)

        keyboardViewManager = KeyboardViewManager(this, keyboardData)

        if (prefs.isEnableSpeechRecognition.get() && prefs.modelsOrder.get().isNotEmpty()) {
            modelManager = ModelManager(this, this)

            modelManager.initializeFirstLocale()

            textManager = TextManager(this, modelManager)
        }

        actionManager = ActionManager(this)


    }

    override fun onBindInput() {
        Log.d("IME", "@onBindInput")

        if (prefs.isEnableSpeechRecognition.get() && prefs.modelsOrder.get().isNotEmpty()) {
            modelManager.reloadModels()
            modelManager.initializeFirstLocale()
        }
    }

    override fun onWindowShown() {
        super.onWindowShown()
        lifecycleOwner.onResume()
    }

    override fun onWindowHidden() {
        super.onWindowHidden()
        lifecycleOwner.onPause()
    }

    override fun onStartInputView(info: EditorInfo, restarting: Boolean) {
        lifecycleOwner.onResume()
        editorInfo = info
        isRichTextEditor =
            editorInfo.inputType and InputType.TYPE_MASK_CLASS != EditorInfo.TYPE_NULL ||
                    editorInfo.initialSelStart >= 0 && editorInfo.initialSelEnd >= 0 // based on florisboard code

        val mIMM = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        keyboardData = keyboardData.copy(
            locale = mIMM.currentInputMethodSubtype?.languageTag!!,
            enterAction = findEnterAction(),
            inputType = info.inputType.and(EditorInfo.IME_MASK_ACTION),
            token = token
        )

        if (prefs.isEnableSpeechRecognition.get() && prefs.modelsOrder.get().isNotEmpty()) {
            if (currentRecognizerSource != null && currentRecognizerSource!!.closed) {
                Log.d("IMEService", "Initializing Recognizer in StartInputView Scope")
                modelManager.initializeFirstLocale()
            }
        }

        setInputView(KeyboardViewManager(this, keyboardData))
        setKeepScreenOn(true)
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        setKeepScreenOn(false)

        lifecycleOwner.onPause()

        if (prefs.isEnableSpeechRecognition.get() && prefs.modelsOrder.get().isNotEmpty()) {
            if (modelManager.isRunning) {
                modelManager.stop()
            }
        }

        startCapsHandler()
    }

    override fun onCreateInputView(): View {
        val windowCompat = myWindow

        if (windowCompat != null) {
            lifecycleOwner.attachToDecorView(windowCompat.decorView)

            windowCompat.navigationBarColor = Color.Transparent.toArgb()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                windowCompat.setDecorFitsSystemWindows(false)
            } else {
                ViewCompat.setOnApplyWindowInsetsListener(keyboardViewManager) { _, insets ->
                    @Suppress("DEPRECATION")
                    insets.replaceSystemWindowInsets(
                        0,
                        0,
                        0,
                        5
                    )
                }
            }

            WindowCompat.getInsetsController(windowCompat, keyboardViewManager).apply {
                hide(WindowInsetsCompat.Type.navigationBars())
            }
        }

        actionManager.onCreateInputView()

        return keyboardViewManager
    }

    override fun onUpdateSelection(
        oldSelStart: Int,
        oldSelEnd: Int,
        newSelStart: Int,
        newSelEnd: Int,
        candidatesStart: Int,
        candidatesEnd: Int
    ) {
        super.onUpdateSelection(
            oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd
        )

        actionManager.updateSelection(newSelStart, newSelEnd)


        if (prefs.isEnableSpeechRecognition.get() && prefs.modelsOrder.get().isNotEmpty()) {
            if (currentRecognizerSource != null && currentRecognizerSource!!.closed) {
                textManager.onUpdateSelection(newSelStart, newSelEnd)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleOwner.onDestroy()

        if (prefs.isEnableSpeechRecognition.get() && prefs.modelsOrder.get().isNotEmpty()) {
            if (currentRecognizerSource != null && currentRecognizerSource!!.closed) {
                modelManager.onDestroy()
            }
        }
    }

    override fun onResult(text: String) {
        Log.d("VoskIME", "Result: $text")
        val isEnabled = prefs.isEnableSpeechRecognition.get()
        val modelsList = prefs.modelsOrder.get()

        if (text.isEmpty() || !isEnabled || modelsList.isEmpty()) return

        textManager.onText(text, TextManager.Mode.STANDARD)
    }

    override fun onFinalResult(text: String) {
        Log.d("VoskIME", "Final result: $text")
        val isEnabled = prefs.isEnableSpeechRecognition.get()
        val modelsList = prefs.modelsOrder.get()

        if (text.isEmpty() || !isEnabled || modelsList.isEmpty()) return

        textManager.onText(text, TextManager.Mode.FINAL)
    }

    override fun onPartialResult(partialText: String) {
        Log.d("VoskIME", "Partial result: $partialText")
        val isEnabled = prefs.isEnableSpeechRecognition.get()
        val modelsList = prefs.modelsOrder.get()

        if (partialText == "" || !isEnabled || modelsList.isEmpty()) return

        textManager.onText(partialText, TextManager.Mode.PARTIAL)
    }

    override fun onStateChanged(state: ModelManager.State) {
        val isEnabled = prefs.isEnableSpeechRecognition.get()
        val modelsList = prefs.modelsOrder.get()

        if (!isEnabled || modelsList.isEmpty()) return

        if (state != ModelManager.State.STATE_STOPPED) {
            prefs.recognitionState.set(
                when (state) {
                    ModelManager.State.STATE_LOADING -> STATE_LOADING
                    ModelManager.State.STATE_READY -> STATE_READY
                    ModelManager.State.STATE_LISTENING -> STATE_LISTENING
                    ModelManager.State.STATE_PAUSED -> STATE_PAUSED
                    ModelManager.State.STATE_ERROR -> STATE_ERROR
                    else -> STATE_INITIAL
                }
            )
        }
    }

    override fun onError(type: ModelManager.ErrorType) {
        val isEnabled = prefs.isEnableSpeechRecognition.get()
        val modelsList = prefs.modelsOrder.get()

        if (!isEnabled || modelsList.isEmpty()) return

        when (type) {
            ModelManager.ErrorType.NO_RECOGNIZERS_INSTALLED -> {
                Toast.makeText(
                    this,
                    "No Offline Voice Model Found, Download in settings!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onError(e: Exception) {
        val isEnabled = prefs.isEnableSpeechRecognition.get()
        val modelsList = prefs.modelsOrder.get()

        if (!isEnabled || modelsList.isEmpty()) return

        prefs.recognitionState.set(STATE_ERROR)
    }

    override fun onRecognizerSource(source: RecognizerSource) {
        val isEnabled = prefs.isEnableSpeechRecognition.get()
        val modelsList = prefs.modelsOrder.get()

        if (!isEnabled || modelsList.isEmpty()) return

//        currentRecognizerSource?.stateLD?.removeObserver(keyboardViewManager)
        currentRecognizerSource = source
//        source.stateLD.observe(lifecycleOwner, keyboardViewManager)
    }

    override fun onTimeout() {
        val isEnabled = prefs.isEnableSpeechRecognition.get()
        val modelsList = prefs.modelsOrder.get()

        if (!isEnabled || modelsList.isEmpty()) return

        prefs.recognitionState.set(STATE_PAUSED)
    }

    companion object {
        private val editorActions = intArrayOf(
            EditorInfo.IME_ACTION_UNSPECIFIED,
            EditorInfo.IME_ACTION_NONE,
            EditorInfo.IME_ACTION_GO,
            EditorInfo.IME_ACTION_SEARCH,
            EditorInfo.IME_ACTION_SEND,
            EditorInfo.IME_ACTION_NEXT,
            EditorInfo.IME_ACTION_DONE,
            EditorInfo.IME_ACTION_PREVIOUS
        )

        const val STATE_INITIAL = 0
        const val STATE_LOADING = 1
        const val STATE_READY = 2
        const val STATE_LISTENING = 3
        const val STATE_PAUSED = 4
        const val STATE_ERROR = 5
    }
}