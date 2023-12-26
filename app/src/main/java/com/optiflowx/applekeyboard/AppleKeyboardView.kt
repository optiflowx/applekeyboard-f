package com.optiflowx.applekeyboard

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.ui.AppleKeyboardIMETheme
import com.optiflowx.applekeyboard.views.KeyboardView

class AppleKeyboardView(context: Context) : AbstractComposeView(context) {
    @Composable
    override fun Content() {
        val width = LocalConfiguration.current.screenWidthDp
        val colors = MaterialTheme.colors

        val viewModel = viewModel<KeyboardViewModel>(
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return KeyboardViewModel(width, colors) as T
                }
            }
        )

        val keyboardSize = viewModel.keyboardSize.observeAsState().value!!


        viewModel.bottomPaddingValue.value =WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        val bottomPadding = viewModel.bottomPaddingValue.observeAsState().value!!

        AppleKeyboardIMETheme {
            Box(
                modifier = Modifier
                    .height(keyboardSize.y.dp + bottomPadding)
                    .width(keyboardSize.x.dp)
            ) {
//            FullSizeBlur(height = keyboardSize.y, width = keyboardSize.x, content= {
//
//            })
                Card(
                    backgroundColor = MaterialTheme.colors.background.copy(alpha = 1f),
                    modifier = Modifier.fillMaxSize()
                ) { KeyboardView() }
            }
        }
        isSystemInDarkTheme()
    }
}
