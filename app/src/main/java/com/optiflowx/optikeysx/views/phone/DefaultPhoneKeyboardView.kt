package com.optiflowx.optikeysx.views.phone

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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

class DefaultPhoneKeyboardView(context: Context) : AbstractComposeView(context) {
    @Composable
    override fun Content() {
        val config = LocalConfiguration.current

        @Suppress("UNCHECKED_CAST")
        val viewModel = viewModel<KeyboardViewModel>(
            key = "KeyboardViewModel",
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return KeyboardViewModel(context) as T
                }
            }
        )

        val orientation = rememberSaveable(config.orientation) {
            mutableIntStateOf(config.orientation)
        }


        DisposableEffect(Unit) {
            viewModel.initSoundIDs(context)

            onDispose { viewModel.onDisposeSoundIDs() }
        }

        AppleKeyboardIMETheme {
            Box(Modifier.wrapContentSize()) {
                Card(
                    shape = RectangleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                ) {
                    if (orientation.intValue == Configuration.ORIENTATION_PORTRAIT) {
                        PhonePortraitKeyboard(viewModel)
                    } else PhoneLandscapeKeyboard(viewModel)
                }
            }

            isSystemInDarkTheme()
        }
    }
}