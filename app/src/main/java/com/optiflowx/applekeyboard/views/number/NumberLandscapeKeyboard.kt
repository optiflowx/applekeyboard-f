package com.optiflowx.applekeyboard.views.number

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.core.preferences.PrefsConstants
import com.optiflowx.applekeyboard.core.preferences.rememberPreference
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.global.KeyboardOptionsView
import com.optiflowx.applekeyboard.views.global.NumberKeyboardActionView

@Composable
fun NumberLandscapeKeyboard(viewModel: KeyboardViewModel) {
    val locale by rememberPreference(PrefsConstants.LOCALE_KEY, "English")
    val fontType by rememberPreference(PrefsConstants.FONT_TYPE_KEY, "Regular")
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val viewWidth = (screenWidth * 0.8)
    val sideWidth = (screenWidth * 0.1)

    Box(
        Modifier
            .mandatorySystemGesturesPadding()
    ) {

        KeyboardOptionsView(viewModel, locale, fontType, viewWidth, 32, 14)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NumberKeyboardActionView(locale, viewWidth.dp)

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

                NumberKeyboardView(viewModel, viewWidth.dp, 36, 8)

                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .width(sideWidth.dp)
                        .padding(bottom = 2.dp)
                ) {}
            }
        }
    }
}