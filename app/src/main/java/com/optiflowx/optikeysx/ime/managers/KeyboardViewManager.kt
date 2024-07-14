package com.optiflowx.optikeysx.ime.managers

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.optikeysx.core.data.KeyboardData
import com.optiflowx.optikeysx.ime.services.IMEService
import com.optiflowx.optikeysx.ime.theme.AppleKeyboardIMETheme
import com.optiflowx.optikeysx.ime.viewmodel.KeyboardViewModel
import com.optiflowx.optikeysx.ime.views.defaults.DefaultLandscapeKeyboard
import com.optiflowx.optikeysx.ime.views.defaults.DefaultPortraitKeyboard
import com.optiflowx.optikeysx.ime.views.number.NumberLandscapeKeyboard
import com.optiflowx.optikeysx.ime.views.number.NumberPortraitKeyboard
import com.optiflowx.optikeysx.ime.views.phone.PhoneLandscapeKeyboard
import com.optiflowx.optikeysx.ime.views.phone.PhonePortraitKeyboard
import splitties.experimental.ExperimentalSplittiesApi
import splitties.views.InputType

@SuppressLint("ViewConstructor")
class KeyboardViewManager(context: Context, private val data: KeyboardData) :
    AbstractComposeView(context) {

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
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
                    return KeyboardViewModel(context as IMEService) as T
                }
            }
        )

        LaunchedEffect(data) {
            viewModel.initKeyboardData(data)

            viewModel.updateSuggestionsState()
//
//            if (viewModel.prefs.autoSwitchIBackIME.get()) {
//                viewModel.onUpdateKeyboardType(KeyboardType.Normal)
//            }
        }

        DisposableEffect(Unit) {
            viewModel.initSoundIDs(context)

            onDispose {
                viewModel.onDisposeSoundIDs()
            }
        }

        AppleKeyboardIMETheme {
            Surface(
                shape = RectangleShape,
                modifier = Modifier.wrapContentSize(),
                color = MaterialTheme.colorScheme.background,
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
}
