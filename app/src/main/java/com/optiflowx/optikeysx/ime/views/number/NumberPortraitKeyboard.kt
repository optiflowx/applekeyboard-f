package com.optiflowx.optikeysx.ime.views.number

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.optiflowx.optikeysx.ime.components.NumberKeyboardActionView
import com.optiflowx.optikeysx.ime.viewmodel.KeyboardViewModel

@Composable
fun NumberPortraitKeyboard(viewModel: KeyboardViewModel) {
    val viewWidth = LocalConfiguration.current.screenWidthDp.dp
    val locale = viewModel.keyboardData.collectAsStateWithLifecycle().value.locale
    

    Box(
        Modifier.navigationBarsPadding()
    ) {
        Column {
            NumberKeyboardActionView(locale, viewWidth)

            NumberKeyboardView(viewModel , viewWidth)
        }
    }
}