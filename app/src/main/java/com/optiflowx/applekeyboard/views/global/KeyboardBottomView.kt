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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun KeyboardBottomView(viewModel: KeyboardViewModel, locale: String, fontType: String) {
    val keyboardWidth = LocalConfiguration.current.screenWidthDp

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(horizontal = (keyboardWidth * 0.075).dp)
            .fillMaxWidth()
    ) {
        KeyboardOptionsView(viewModel, locale, fontType)

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
                    ) { viewModel.isShowOptions.value = true }
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
