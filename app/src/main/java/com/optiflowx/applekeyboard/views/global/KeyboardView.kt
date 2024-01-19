package com.optiflowx.applekeyboard.views.global

import android.view.inputmethod.EditorInfo
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.utils.KeyboardType
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.emoji.EmojiKeyboardView
import com.optiflowx.applekeyboard.views.normal.NormalKeyboardView
import com.optiflowx.applekeyboard.views.number.NumberKeyboardView
import com.optiflowx.applekeyboard.views.phone.PhoneNumberKeyboardView
import com.optiflowx.applekeyboard.views.symbols.SymbolAKeyboardView
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


    Box(
        Modifier.mandatorySystemGesturesPadding()
    ) {
        Column {
            if (keyboardType.value != KeyboardType.Symbol && keyboardType.value != KeyboardType.Emoji) {
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
            //Global Spacer
            Spacer(modifier = Modifier.height(18.dp))

            if (keyboardType.value != KeyboardType.Number && keyboardType.value != KeyboardType.Phone) {
                KeyboardBottomView(viewModel)
            }
        }
    }
}