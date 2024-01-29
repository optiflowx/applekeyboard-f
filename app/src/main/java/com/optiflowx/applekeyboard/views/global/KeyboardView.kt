package com.optiflowx.applekeyboard.views.global

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.view.inputmethod.EditorInfo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.optiflowx.applekeyboard.core.enums.KeyboardType
import com.optiflowx.applekeyboard.core.preferences.PreferencesConstants
import com.optiflowx.applekeyboard.core.services.IMEService
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.clipboard.ClipboardKeyboardView
import com.optiflowx.applekeyboard.views.emoji.EmojiKeyboardView
import com.optiflowx.applekeyboard.views.normal.NormalKeyboardView
import com.optiflowx.applekeyboard.views.number.NumberKeyboardView
import com.optiflowx.applekeyboard.views.phone.PhoneNumberKeyboardView
import com.optiflowx.applekeyboard.views.symbols.SymbolsKeyboardView
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import splitties.experimental.ExperimentalSplittiesApi
import splitties.views.InputType

//Should be stable!

@Composable
@OptIn(ExperimentalSplittiesApi::class)
fun KeyboardView(viewModel: KeyboardViewModel) {
    val context = LocalContext.current
    val config = LocalConfiguration.current

    val keyboardType = viewModel.keyboardType.observeAsState()

    val orientation = rememberSaveable(config.orientation) {
        mutableIntStateOf(config.orientation)
    }

    LaunchedEffect(Unit) {
        while (true) {
            val editor = (context as IMEService).currentInputEditorInfo

            when (editor.inputType and EditorInfo.IME_MASK_ACTION) {
                InputType.number.value -> {
                    viewModel.keyboardType.value = KeyboardType.Number
                }

                InputType.phone.value -> {
                    viewModel.keyboardType.value = KeyboardType.Phone
                }

                else -> {
                    viewModel.keyboardType.value = when (keyboardType.value) {
                        KeyboardType.Emoji, KeyboardType.Symbol, KeyboardType.Clipboard -> keyboardType.value
                        else -> KeyboardType.Normal
                    }
                }
            }
            this.run {
                yield()
                delay(2500)
            }
        }
    }

    if (orientation.intValue == ORIENTATION_PORTRAIT) {
        PortraitKeyboard(keyboardType, viewModel)
    } else LandscapeKeyboard(keyboardType, viewModel)
}