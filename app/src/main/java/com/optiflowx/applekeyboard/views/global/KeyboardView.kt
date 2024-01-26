package com.optiflowx.applekeyboard.views.global

import android.view.inputmethod.EditorInfo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
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
import com.optiflowx.applekeyboard.views.symbols.SymbolAKeyboardView
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import splitties.experimental.ExperimentalSplittiesApi
import splitties.views.InputType

//Should be stable!

@Composable
@OptIn(ExperimentalSplittiesApi::class)
fun KeyboardView(viewModel: KeyboardViewModel) {
    val context = LocalContext.current

    val keyboardType = viewModel.keyboardType.observeAsState()

    val showTopView = (keyboardType.value != KeyboardType.Symbol)

    val showBottomView = (keyboardType.value != KeyboardType.Number
            && keyboardType.value != KeyboardType.Phone)

    val locale = viewModel.preferences.getFlowPreference(
        PreferencesConstants.LOCALE_KEY, "English"
    ).collectAsStateWithLifecycle("English").value

    val fontType = viewModel.preferences.getFlowPreference(
        PreferencesConstants.FONT_TYPE_KEY, "Regular"
    ).collectAsStateWithLifecycle("Regular").value

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
            yield()
            delay(1000)
        }
    }

    Box(
        Modifier
            .mandatorySystemGesturesPadding()
    ) {
        Column {
            if (showTopView) KeyboardTopView(viewModel, locale, keyboardType)

//            AnimatedContent(
//                keyboardType.value,
//                label = "KeyboardView",
//                transitionSpec = { (fadeIn()).togetherWith(fadeOut()) },
//            ) { type ->
            when (keyboardType.value!!) {
                KeyboardType.Normal -> NormalKeyboardView(viewModel)

                KeyboardType.Symbol -> SymbolAKeyboardView(viewModel)

                KeyboardType.Number -> NumberKeyboardView(viewModel)

                KeyboardType.Phone -> PhoneNumberKeyboardView(viewModel)

                KeyboardType.Emoji -> EmojiKeyboardView(viewModel)

                KeyboardType.Clipboard -> ClipboardKeyboardView(viewModel)
            }
//            }

            if (showBottomView) KeyboardBottomView(viewModel, locale, fontType)
        }
    }
}