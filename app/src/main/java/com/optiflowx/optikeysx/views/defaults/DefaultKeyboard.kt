

package com.optiflowx.optikeysx.views.defaults

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
import com.optiflowx.optikeysx.ui.AppleKeyboardIMETheme
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel

@SuppressLint("ViewConstructor")
class DefaultKeyboard(context: Context, val locale: String) : AbstractComposeView(context) {
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

        LaunchedEffect(locale) {
            viewModel.initLocale(locale)
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
                if (orientation.intValue == Configuration.ORIENTATION_PORTRAIT) {
                    DefaultPortraitKeyboard(viewModel)
                } else DefaultLandscapeKeyboard(viewModel)
            }

            isSystemInDarkTheme()
        }
    }
}
