package com.optiflowx.applekeyboard.views.global

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.core.preferences.PrefsConstants
import com.optiflowx.applekeyboard.core.preferences.rememberPreference
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun KeyboardBottomView(viewModel: KeyboardViewModel) {
    val keyboardWidth = LocalConfiguration.current.screenWidthDp.dp

    val fontType by rememberPreference(PrefsConstants.FONT_TYPE_KEY, "Regular")

    val locale by rememberPreference(PrefsConstants.LOCALE_KEY, "English")

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .layoutId("bottomView")
            .padding(horizontal = (keyboardWidth.value * 0.075).dp)
            .fillMaxWidth()
    ) {
        KeyboardOptionsView(viewModel, locale, fontType, keyboardWidth)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.height(48.dp).fillMaxWidth().fillMaxHeight()
        ) {
            Icon(
                painter = painterResource(R.drawable.globe_outline),
                contentDescription = "globe",
                tint = MaterialTheme.colorScheme.scrim,
                modifier = Modifier
                    .size(30.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        role = Role.Button,
                    ) { viewModel.updateIsShowOptions(true)  }
            )

            Icon(
                painter = painterResource(
                    if (isSystemInDarkTheme()) R.drawable.mic_fill else R.drawable.mic_outline
                ),
                contentDescription = "microphone",
                tint = MaterialTheme.colorScheme.scrim,
                modifier = Modifier
                    .size(30.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        role = Role.Button,
                    ) { }
            )
        }
    }
}
