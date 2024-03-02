package com.optiflowx.optikeysx.ui.keyboard

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.enums.KeyboardType
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import dev.patrickgold.jetpref.datastore.model.observeAsState

@Composable
fun KeyboardBottomView(viewModel: KeyboardViewModel) {
    val keyboardWidth = LocalConfiguration.current.screenWidthDp.dp
    val keyboardType = viewModel.keyboardType.collectAsState().value
    val fontType = viewModel.prefs.keyboardFontType.observeAsState().value

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .layoutId("bottomView")
            .padding(horizontal = (keyboardWidth.value * 0.075).dp)
            .fillMaxWidth()
    ) {


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .height(44.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box {
                KeyboardGlobalOptions(viewModel, fontType, keyboardWidth)
                Icon(
                    painter = painterResource(R.drawable.globe_outline),
                    contentDescription = "globe",
                    tint = MaterialTheme.colorScheme.scrim,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            role = Role.Button,
                        ) { viewModel.updateIsShowOptions(true) }
                )
            }

            Icon(
                painter = painterResource(
                    if (keyboardType != KeyboardType.Recognizer) {
                        if (isSystemInDarkTheme())
                            R.drawable.mic_fill
                        else R.drawable.mic_outline
                    } else R.drawable.abc
                ),
                contentDescription = "microphone",
                tint = MaterialTheme.colorScheme.scrim,
                modifier = Modifier
                    .size(28.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        role = Role.Button,
                    ) {
                        if (keyboardType != KeyboardType.Recognizer) {
                            viewModel.onUpdateKeyboardType(KeyboardType.Recognizer)
                        } else viewModel.onUpdateKeyboardType(KeyboardType.Normal)
                    }
            )
        }
    }
}
