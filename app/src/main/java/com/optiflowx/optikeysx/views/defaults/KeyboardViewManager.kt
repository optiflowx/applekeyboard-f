package com.optiflowx.optikeysx.views.defaults

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.optikeysx.core.data.KeyboardData
import com.optiflowx.optikeysx.ime.recognizers.RecognizerState
import com.optiflowx.optikeysx.optikeysxPreferences
import com.optiflowx.optikeysx.ui.AppleKeyboardIMETheme
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import com.optiflowx.optikeysx.views.number.NumberLandscapeKeyboard
import com.optiflowx.optikeysx.views.number.NumberPortraitKeyboard
import com.optiflowx.optikeysx.views.phone.PhoneLandscapeKeyboard
import com.optiflowx.optikeysx.views.phone.PhonePortraitKeyboard
import splitties.experimental.ExperimentalSplittiesApi
import splitties.views.InputType

@SuppressLint("ViewConstructor")
class KeyboardViewManager(context: Context, private val data: KeyboardData) :
    AbstractComposeView(context),
    Observer<RecognizerState> {
    private val prefs by optikeysxPreferences()
    val stateLD = MutableLiveData(STATE_INITIAL)
    val errorMessageLD = MutableLiveData("Error")
    private var listener: Listener? = null
    val recognizerNameLD = MutableLiveData("")


    init {
        this.isSoundEffectsEnabled = true

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)


//        Log.v(TAG, recognizerNameLD.value.toString())
//        Log.v(TAG, enterActionLD.value.toString())
        Log.v(TAG, stateLD.value.toString())
    }

    @OptIn(ExperimentalSplittiesApi::class)
    @Composable
    override fun Content() {

        val config = LocalConfiguration.current

        val orientation = rememberSaveable(config.orientation) {
            mutableIntStateOf(config.orientation)
        }

        val viewModel = viewModel<KeyboardViewModel>(
            key = "KeyboardViewModel",
            factory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return KeyboardViewModel(context) as T
                }
            }
        )

        LaunchedEffect(data) {
            viewModel.initKeyboardData(data)

            Log.i(TAG, "viewModel.locale: ${viewModel.keyboardData.value.locale}")
        }

        DisposableEffect(Unit) {
            viewModel.initSoundIDs(context)

            onDispose { viewModel.onDisposeSoundIDs() }
        }

        AppleKeyboardIMETheme {
            Surface(
                shape = RectangleShape,
                modifier = Modifier.wrapContentSize(),
                color = MaterialTheme.colorScheme.background.copy(
                    alpha = 1f
                ),
            ) {
                when (data.inputType) {
                    InputType.number.value -> {
                        if (orientation.intValue == Configuration.ORIENTATION_PORTRAIT) {
                            NumberPortraitKeyboard(viewModel)
                        } else NumberLandscapeKeyboard(viewModel)
                    }

                    InputType.phone.value -> {
                        if (orientation.intValue == Configuration.ORIENTATION_PORTRAIT) {
                            PhonePortraitKeyboard(viewModel)
                        } else PhoneLandscapeKeyboard(viewModel)
                    }

                    else -> {
                        if (orientation.intValue == Configuration.ORIENTATION_PORTRAIT) {
                            DefaultPortraitKeyboard(viewModel)
                        } else DefaultLandscapeKeyboard(viewModel)
                    }
                }
            }

            isSystemInDarkTheme()
        }
    }

    override fun onChanged(value: RecognizerState) {
        when (value) {
            RecognizerState.CLOSED, RecognizerState.NONE -> stateLD.setValue(STATE_INITIAL)

            RecognizerState.LOADING -> stateLD.setValue(STATE_LOADING)
            RecognizerState.READY -> stateLD.setValue(STATE_READY)
            RecognizerState.IN_RAM -> stateLD.setValue(STATE_PAUSED)
            RecognizerState.ERROR -> stateLD.setValue(STATE_ERROR)
        }
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun micClick()
        fun micLongClick(): Boolean
        fun backClicked()
        fun backspaceClicked()
        fun backspaceTouchStart(offset: Offset)
        fun backspaceTouched(change: PointerInputChange, dragAmount: Float)
        fun backspaceTouchEnd()
        fun returnClicked()
        fun modelClicked()
        fun settingsClicked()
        fun buttonClicked(text: String)
    }

    companion object {
        const val STATE_INITIAL = 0
        const val STATE_LOADING = 1
        const val STATE_READY = 2 // model loaded, ready to start
        const val STATE_LISTENING = 3
        const val STATE_PAUSED = 4
        const val STATE_ERROR = 5
        const val TAG = "KeyboardViewManager"
    }
}
