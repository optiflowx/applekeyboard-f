package com.optiflowx.optikeysx.views.number

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.core.preferences.PrefsConstants
import com.optiflowx.optikeysx.core.preferences.rememberPreference
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import com.optiflowx.optikeysx.views.global.NumberKeyboardActionView

@Composable
fun NumberPortraitKeyboard(viewModel: KeyboardViewModel) {
    val locale by rememberPreference(PrefsConstants.LOCALE_KEY, "English")

    val viewWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        Modifier
            .mandatorySystemGesturesPadding()
    ) {
        Column {
            NumberKeyboardActionView(locale, viewWidth)

            NumberKeyboardView(viewModel , viewWidth)
        }
    }
}