package com.optiflowx.optikeysx.views.phone

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.ui.keyboard.NumberKeyboardActionView
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel

@Composable
fun PhonePortraitKeyboard(viewModel: KeyboardViewModel) {
    
    val viewWidth = LocalConfiguration.current.screenWidthDp.dp
    val locale = viewModel.keyboardData.collectAsState().value.locale

    Box(
        Modifier.navigationBarsPadding()
    ) {
        Column {
            NumberKeyboardActionView(locale, viewWidth)

            PhoneKeyboardView(viewModel , viewWidth)
        }
    }
}