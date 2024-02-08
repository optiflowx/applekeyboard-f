// Copyright 2019 Alpha Cephei Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.optiflowx.optikeysx.core.data.KeyboardData
import com.optiflowx.optikeysx.core.enums.KeepScreenAwakeMode
import com.optiflowx.optikeysx.optikeysxPreferences
import com.optiflowx.optikeysx.views.defaults.KeyboardViewManager
import org.vosk.BuildConfig
import org.vosk.LibVosk
import org.vosk.LogLevel
import org.vosk.android.RecognitionListener
import kotlin.math.roundToInt

class IMEService : InputMethodService(), RecognitionListener {
    private val prefs by optikeysxPreferences()

    val lifecycleOwner = IMELifecycleOwner()

    private lateinit var editorInfo: EditorInfo
    private lateinit var keyboardViewManager: KeyboardViewManager
    private lateinit var modelManager: ModelManager
    private lateinit var actionManager: ActionManager
    private lateinit var textManager: TextManager

    var keyboardData = KeyboardData()

    var isRichTextEditor = true
        private set

    val token: IBinder?
        get() {
            val window = myWindow ?: return null
            return window.attributes.token
        }

    private val myWindow: Window?
        get() {
            return window.window
        }

//    @OptIn(ExperimentalSplittiesApi::class)
//    private fun setKeyboardView(): View {
//        val inputType = editorInfo.inputType.and(EditorInfo.IME_MASK_ACTION)
//
//        val mIMM = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//
//        val locale = mIMM.currentInputMethodSubtype?.languageTag!!
//
//        return when (inputType) {
////            IT.number.value -> DefaultNumberKeyboardView(this, locale)
////
////            IT.phone.value -> DefaultPhoneKeyboardView(this, locale)
//
//            else -> {
//                KeyboardViewManager(this, locale)
//            }
//        }
//    }

    override fun onCreate() {
        super.onCreate()
        lifecycleOwner.onCreate()
        LibVosk.setLogLevel(if (BuildConfig.DEBUG) LogLevel.INFO else LogLevel.WARNINGS)

        keyboardViewManager = KeyboardViewManager(this, keyboardData)
        actionManager = ActionManager(this)
        modelManager = ModelManager(this, keyboardViewManager)
        textManager = TextManager(this, modelManager)
    }

    private fun setKeepScreenOn(keepScreenOn: Boolean) {
        val window = myWindow ?: return
        if (keepScreenOn) window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) else window.clearFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
    }

    override fun onStartInputView(info: EditorInfo, restarting: Boolean) {
        lifecycleOwner.onResume()
        editorInfo = info
        isRichTextEditor =
            editorInfo.inputType and InputType.TYPE_MASK_CLASS != EditorInfo.TYPE_NULL ||
                    editorInfo.initialSelStart >= 0 && editorInfo.initialSelEnd >= 0 // based on florisboard code
        modelManager.onResume()
        textManager.onResume()

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
        modelManager.stop()

        setKeepScreenOn(false)
        modelManager.stop()
        if (prefs.autoSwitchIBackIME.get()) {
            // switch back
            actionManager.switchToLastIme(false)
        }
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

        keyboardViewManager.setListener(object : KeyboardViewManager.Listener {
            override fun micClick() {
                if (modelManager.openSettingsOnMic) {
                    actionManager.openSettings()
                } else if (modelManager.isRunning) {
                    if (modelManager.isPaused) {
                        modelManager.pause(false)
                        if (prefs.keepScreenAwake.get() == KeepScreenAwakeMode.WHEN_LISTENING) setKeepScreenOn(
                            true
                        )
                    } else {
                        modelManager.pause(true)
                        if (prefs.keepScreenAwake.get() == KeepScreenAwakeMode.WHEN_LISTENING) setKeepScreenOn(
                            false
                        )
                    }
                } else {
                    modelManager.start()
                    if (prefs.keepScreenAwake.get() == KeepScreenAwakeMode.WHEN_LISTENING) setKeepScreenOn(
                        true
                    )
                }
            }

            override fun micLongClick(): Boolean {
                val imeManager =
                    applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imeManager.showInputMethodPicker()
                return true
            }

            override fun backClicked() {
                actionManager.switchToLastIme(true)
            }

            override fun backspaceClicked() {
                actionManager.deleteLastChar()
            }

            private var initX = 0f
            private var initY = 0f
            private val threshold = resources.displayMetrics.densityDpi / 6f
            private val charLen = resources.displayMetrics.densityDpi / 32f
            private var swiping = false
            private var restart = false

            override fun backspaceTouchStart(offset: Offset) {
                restart = true
                swiping = false
            }

            override fun backspaceTouched(change: PointerInputChange, dragAmount: Float) {
                if (restart) {
                    restart = false
                    initX = change.position.x
                    initY = change.position.y
                }

                var x = change.position.x - initX
                val y = change.position.y - initY

                Log.d("IME", "$x,$y")

                if (x < -threshold) {
                    swiping = true
                }

                if (swiping) {
                    x = -x // x is negative
                    val amount = ((x - threshold) / charLen).roundToInt()
                    actionManager.selectCharsBack(amount)
                }
            }

            override fun backspaceTouchEnd() {
                if (swiping) actionManager.deleteSelection()
            }

            override fun returnClicked() {
                actionManager.sendEnter()
            }

            override fun modelClicked() {
                modelManager.switchToNextRecognizer()
            }

            override fun settingsClicked() {
                actionManager.openSettings()
            }

            override fun buttonClicked(text: String) {
                textManager.onText(text, TextManager.Mode.INSERT)
            }
        })

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

        actionManager.updateSelection(
            newSelStart, newSelEnd
        )

        textManager.onUpdateSelection(newSelStart, newSelEnd)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleOwner.onDestroy()
        modelManager.onDestroy()
    }

    override fun onResult(text: String) {
        Log.d("VoskIME", "Result: $text")
        if (text.isEmpty()) return
        textManager.onText(text, TextManager.Mode.STANDARD)
    }

    override fun onFinalResult(text: String) {
        Log.d("VoskIME", "Final result: $text")
        if (text.isEmpty()) return
        textManager.onText(text, TextManager.Mode.FINAL)
    }

    override fun onPartialResult(partialText: String) {
        Log.d("VoskIME", "Partial result: $partialText")
        if (partialText == "") return

        textManager.onText(partialText, TextManager.Mode.PARTIAL)
    }

    override fun onError(e: Exception) {
        keyboardViewManager.errorMessageLD.postValue("Recognition error: ${e.message}")
        keyboardViewManager.stateLD.postValue(KeyboardViewManager.STATE_ERROR)
    }

    override fun onTimeout() {
        keyboardViewManager.stateLD.postValue(KeyboardViewManager.STATE_PAUSED)
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