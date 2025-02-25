package com.optiflowx.optikeysx.views.number

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.optiflowx.optikeysx.ui.keyboard.NumberKeyboardActionView
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel

@Composable
fun NumberLandscapeKeyboard(viewModel: KeyboardViewModel) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val viewWidth = (screenWidth * 0.8).dp
    val sideWidth = (screenWidth * 0.1)
    val locale = viewModel.keyboardData.collectAsStateWithLifecycle().value.locale
    

    Column(
        modifier = Modifier.mandatorySystemGesturesPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NumberKeyboardActionView(locale, viewWidth)

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.padding(vertical = 2.dp)
        ) {

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .width(sideWidth.dp)
                    .padding(bottom = 2.dp)
            ) {}

            NumberKeyboardView(viewModel, viewWidth, 34, 0)

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .width(sideWidth.dp)
                    .padding(bottom = 2.dp)
            ) {}
        }
    }
}