package com.optiflowx.applekeyboard.composables.keyboard

import android.view.MotionEvent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.popup
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyButton(
    onClick: () -> Unit,
    color: Color,
    buttonWidth: Dp,
    id: String,
    modifier: Modifier = Modifier,
    pressState: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    val enabled = true
    val delayMillis = 150L
    var pressed by remember { mutableStateOf(false) }
    val currentClickListener by rememberUpdatedState(onClick)

    LaunchedEffect(pressed, enabled) {
        if (id == "erase") {
            while (enabled && pressed) {
                currentClickListener()
                delay(delayMillis)
            }
        }

        if (id != "erase" && enabled && pressed) currentClickListener()
    }

    Surface(
        color = color,
        modifier = modifier
            .layoutId(id)
            .width(buttonWidth)
            .semantics {
                popup()
                role = Role.Button
            }
            .pointerInteropFilter {
                pressed = when (it.action) {
                    MotionEvent.ACTION_DOWN -> true

                    else -> false
                }
                pressState(pressed)

                true
            }
            .shadow(
                clip = true,
                elevation = (1.6).dp,
                shape = RoundedCornerShape((5.5).dp),
                ambientColor = Color.Transparent,
            )
    ) { Box(contentAlignment = Alignment.Center) { content() } }
}