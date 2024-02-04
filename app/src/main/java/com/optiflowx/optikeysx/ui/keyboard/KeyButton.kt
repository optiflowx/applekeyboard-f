package com.optiflowx.optikeysx.ui.keyboard

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.core.preferences.PrefsConstants
import com.optiflowx.optikeysx.core.preferences.rememberPreference

@Composable
fun KeyButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "",
    color: Color,
    id: String,
    popupWidth: Float = 0f,
    showPopup: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit,

    ) {
    val isIgnoreElevation = (id == "switch" || id == "period")

    val interactionSource = remember { MutableInteractionSource() }

    val isCharacterPreview = rememberPreference(PrefsConstants.CHARACTER_PREVIEW_KEY, false)

    val pressed = interactionSource.collectIsPressedAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .layoutId(id)
            .fillMaxSize()
    ) {
        if(!isIgnoreElevation) {
            Surface(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(5.5.dp),
                modifier = Modifier
                    .matchParentSize()
                    .absoluteOffset(0.dp, 1.5.dp)
                    .absolutePadding(left = 0.75.dp, right = 0.5.dp)
            ) {}
        }

        Surface(
            color = color,
            enabled = enabled,
            shape = RoundedCornerShape((5.5).dp),
            interactionSource = interactionSource,
            onClick = onClick,
            modifier = modifier.matchParentSize()
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (pressed.value && showPopup && isCharacterPreview.value) {
                    KeyButtonPopup(popupWidth.dp, text)
                }

                content()
            }
        }
    }
}
