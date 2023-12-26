package com.optiflowx.applekeyboard.views

import android.view.inputmethod.EditorInfo
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.utils.KeyboardType
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import splitties.experimental.ExperimentalSplittiesApi
import splitties.views.InputType

@OptIn(ExperimentalSplittiesApi::class)
@Composable
fun KeyboardView() {
    val width = LocalConfiguration.current.screenWidthDp
    val colors = MaterialTheme.colors
    val context = LocalContext.current
    val view = LocalView.current
    val viewModel = viewModel<KeyboardViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return KeyboardViewModel(width, colors) as T
            }
        }
    )

    val keyboardType = viewModel.keyboardType.observeAsState()
    val isNumSym = viewModel.isNumberSymbol.observeAsState().value!!

    LaunchedEffect(Unit) {
        while (true) {
            val editor = (context as IMEService).currentInputEditorInfo
            when (editor.inputType and EditorInfo.IME_MASK_ACTION) {
                InputType.number.value ->
                    viewModel.keyboardType.value = KeyboardType.Number

                InputType.phone.value ->
                    viewModel.keyboardType.value = KeyboardType.Phone

                else -> {
                    if (keyboardType.value != KeyboardType.Emoji) {
                        if(keyboardType.value != KeyboardType.Symbol) {
                            viewModel.keyboardType.value = KeyboardType.Normal
                        }
                    }
                }
            }
            yield()
            delay(1000)
        }
    }

    Column {
        KeyboardTopView(keyboardType)
        AnimatedContent(
            keyboardType,
            label = "KeyboardView",
            transitionSpec = {
                (fadeIn(animationSpec = tween(180, delayMillis = 15)) +
                        scaleIn(initialScale = 0.9f, animationSpec = tween(180, delayMillis = 15)))
                    .togetherWith(fadeOut(animationSpec = tween(15)))
            },
        ) { type ->
            when (type.value!!) {
                KeyboardType.Normal -> NormalKeyboardView()

                KeyboardType.Symbol -> SymbolAKeyboardView()

                KeyboardType.Number -> NumberKeyboardView()

                KeyboardType.Phone -> PhoneNumberKeyboardView()

                KeyboardType.Emoji -> EmojiKeyboardView()
            }
        }
        if (keyboardType.value != KeyboardType.Number && keyboardType.value != KeyboardType.Phone) {
            KeyboardBottomView(viewModel)
        }
    }
}

