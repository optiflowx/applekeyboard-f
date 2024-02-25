package com.optiflowx.optikeysx.ime

import android.inputmethodservice.InputMethodService
import android.os.Build
import android.os.IBinder
import android.text.InputType
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.optiflowx.optikeysx.core.data.KeyboardData
import com.optiflowx.optikeysx.optikeysxPreferences
import com.optiflowx.optikeysx.views.KeyboardViewManager
import org.vosk.BuildConfig
import org.vosk.LibVosk
import org.vosk.LogLevel

class IMEService : InputMethodService() {
    private val prefs by optikeysxPreferences()

    val lifecycleOwner = IMELifecycleOwner()

    private lateinit var editorInfo: EditorInfo
    private lateinit var keyboardViewManager: KeyboardViewManager
    private lateinit var actionManager: ActionManager

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

    override fun onCreate() {
        super.onCreate()
        lifecycleOwner.onCreate()
        LibVosk.setLogLevel(if (BuildConfig.DEBUG) LogLevel.INFO else LogLevel.WARNINGS)

        keyboardViewManager = KeyboardViewManager(this, keyboardData)
        actionManager = ActionManager(this)
    }

    fun setKeepScreenOn(keepScreenOn: Boolean) {
        val window = myWindow ?: return
        if (keepScreenOn) window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) else window.clearFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
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

        setInputView(KeyboardViewManager(this, keyboardData))
        setKeepScreenOn(true)
    }

    private fun findEnterAction(): Int {
        val action = editorInfo.imeOptions and EditorInfo.IME_MASK_ACTION
        if (editorInfo.imeOptions and EditorInfo.IME_FLAG_NO_ENTER_ACTION == 0 && action in editorActions) {
            return action
        }

        return EditorInfo.IME_ACTION_UNSPECIFIED
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        lifecycleOwner.onPause()
        startCapsHandler()
        setKeepScreenOn(false)
    }

    override fun onCreateInputView(): View {
        val windowCompat = window?.window

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
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleOwner.onDestroy()
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
    }
}