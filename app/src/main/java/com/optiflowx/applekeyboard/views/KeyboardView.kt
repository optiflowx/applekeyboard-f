package com.optiflowx.applekeyboard.views

import android.view.inputmethod.EditorInfo
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.utils.KeyboardType
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import splitties.experimental.ExperimentalSplittiesApi
import splitties.views.InputType

@OptIn(ExperimentalSplittiesApi::class)
@Composable
fun KeyboardView(viewModel: KeyboardViewModel) {
    val context = LocalContext.current
    val keyboardType = viewModel.keyboardType.observeAsState()

    LaunchedEffect(Unit) {
        while (true) {
            val editor = (context as IMEService).currentInputEditorInfo
            when (editor.inputType and EditorInfo.IME_MASK_ACTION) {
                InputType.number.value ->
                    viewModel.keyboardType.value = KeyboardType.Number

                InputType.phone.value ->
                    viewModel.keyboardType.value = KeyboardType.Phone

                else -> if (keyboardType.value != KeyboardType.Emoji) {
                    if (keyboardType.value != KeyboardType.Symbol) {
                        viewModel.keyboardType.value = KeyboardType.Normal
                    }
                }
            }
            yield()
            delay(1000)
        }
    }

    Column {
        if (keyboardType.value != KeyboardType.Symbol) {
            KeyboardTopView(viewModel)
        }
        AnimatedContent(
            keyboardType,
            label = "KeyboardView",
            transitionSpec = {
                (fadeIn(animationSpec = tween(250, delayMillis = 5)) +
                        scaleIn(initialScale = 0.9f, animationSpec = tween(250, delayMillis = 5)))
                    .togetherWith(fadeOut(animationSpec = tween(5)))
            },
        ) { type ->
            when (type.value!!) {
                KeyboardType.Normal -> NormalKeyboardView(viewModel)

                KeyboardType.Symbol -> SymbolAKeyboardView(viewModel)

                KeyboardType.Number -> NumberKeyboardView(viewModel)

                KeyboardType.Phone -> PhoneNumberKeyboardView(viewModel)

                KeyboardType.Emoji -> EmojiKeyboardView(viewModel)
            }
        }
        if (keyboardType.value != KeyboardType.Number && keyboardType.value != KeyboardType.Phone) {
            KeyboardBottomView(viewModel)
        }
    }
}

