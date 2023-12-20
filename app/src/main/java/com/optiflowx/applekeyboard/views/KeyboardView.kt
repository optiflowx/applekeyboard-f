package com.optiflowx.applekeyboard.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.utils.KeyboardType

@Composable
fun KeyboardView() {
    val width = LocalConfiguration.current.screenWidthDp
    val colors = MaterialTheme.colors
    val viewModel = viewModel<KeyboardViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return KeyboardViewModel(width, colors) as T
            }
        }
    )

    val keyboardType = viewModel.keyboardType.observeAsState()

    Column {
        KeyboardTopView(keyboardType)
        AnimatedContent(keyboardType, label = "KeyboardView") { type ->
            when (type.value ?: KeyboardType.Normal) {
                KeyboardType.Normal -> {
                    NormalKeyboardView()
                }

                KeyboardType.Symbol -> {
                    SymbolAKeyboardView()
                }

                KeyboardType.Number -> {
                    NumberKeyboardView()
                }

                KeyboardType.NumberSymbol -> {
                    NumberSymbolKeyboardView()
                }

                KeyboardType.Emoji -> {
                    EmojiKeyboardView()
                }
            }
        }
        KeyboardBottomView(viewModel)
    }
}

