package com.optiflowx.optikeysx.ime.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.ime.services.IMEService
import com.optiflowx.optikeysx.optikeysxPreferences
import com.optiflowx.optikeysx.ime.viewmodel.KeyboardViewModel
import dev.patrickgold.jetpref.datastore.model.observeAsState

@Composable
fun KeyboardBottomView(viewModel: KeyboardViewModel) {
    val keyboardWidth = LocalConfiguration.current.screenWidthDp.dp
    val fontType = viewModel.prefs.keyboardFontType.observeAsState().value
    val state = viewModel.prefs.recognitionState.observeAsState().value
    val imeService = LocalContext.current as IMEService
    val prefs by optikeysxPreferences()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .layoutId("bottomView")
            .height(55.dp)
            .fillMaxWidth()
            .padding(horizontal = (keyboardWidth.value * 0.04).dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.matchParentSize()
        ) {
            Box(Modifier.padding(10.dp)) {
                KeyboardGlobalOptions(viewModel, fontType, keyboardWidth)

                Box {
                    Icon(
                        painter = painterResource(R.drawable.globe_outline),
                        contentDescription = "globe",
                        tint = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier
                            .size(27.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                role = Role.Button,
                            ) { viewModel.updateIsShowOptions(true) }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(
                        if (state == 3) {
                            MaterialTheme.colorScheme.scrim
                        } else {
                            MaterialTheme.colorScheme.background
                        }
                    )
                    .padding(10.dp)
            ) {
                if (prefs.modelsOrder.get().isNotEmpty()) {
                    Icon(
                        painter = painterResource(R.drawable.mic_outline),
                        contentDescription = "microphone",
                        tint = if (state == 3) {
                            MaterialTheme.colorScheme.background
                        } else {
                            MaterialTheme.colorScheme.scrim
                        },
                        modifier = Modifier
                            .size(27.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                role = Role.Button,
                            ) { imeService.onRecognition() }
                    )
                }
            }
        }
    }
}
