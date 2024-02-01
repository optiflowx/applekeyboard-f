@file:Suppress("UNCHECKED_CAST")

package com.optiflowx.applekeyboard

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.ui.AppleKeyboardIMETheme
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.global.LandscapeKeyboard
import com.optiflowx.applekeyboard.views.global.PortraitKeyboard


class DefaultKeyboardView(context: Context) : AbstractComposeView(context) {
    @Composable
    override fun Content() {
        val config = LocalConfiguration.current
        val context = LocalContext.current


        val orientation = rememberSaveable(config.orientation) {
            mutableIntStateOf(config.orientation)
        }

        val viewModel = viewModel<KeyboardViewModel>(
            key = "KeyboardViewModel",
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return KeyboardViewModel(context) as T
                }
            }
        )

        DisposableEffect(Unit) {
            viewModel.initSoundIDs(context)

            onDispose { viewModel.onDisposeSoundIDs() }
        }

        AppleKeyboardIMETheme {
            Box(Modifier.wrapContentSize()) {
                Card(
                    shape = RectangleShape,
                    colors = cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                ) {
                    if (orientation.intValue == Configuration.ORIENTATION_PORTRAIT) {
                        PortraitKeyboard(viewModel)
                    } else LandscapeKeyboard(viewModel)
                }
            }

            isSystemInDarkTheme()
        }
    }
}
