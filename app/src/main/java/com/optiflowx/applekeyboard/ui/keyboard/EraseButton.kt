package com.optiflowx.applekeyboard.ui.keyboard

import android.view.MotionEvent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EraseButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color,
    id: String,
    onClick: () -> Unit,
    onRepeatableClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val delayMillis = 115L
    var pressed by remember { mutableStateOf(false) }
    var pressedCount by remember { mutableIntStateOf(0) }
    val currentClickListener by rememberUpdatedState(onRepeatableClick)
    val currentSingleClickListener by rememberUpdatedState(onClick)

    LaunchedEffect(pressed, enabled) {
            while (enabled && pressed) {
                if (pressedCount < 1) {
                    pressedCount += 1
                    currentSingleClickListener()
                } else {
                    currentClickListener()
                    delay(delayMillis)
                }
            }
            pressedCount = 0
    }

    Surface(
        color = color,
        shape = RoundedCornerShape((5.5).dp),
        modifier = modifier
            .layoutId(id)
            .fillMaxSize()
            .pointerInteropFilter {
                pressed = when (it.action) {
                    MotionEvent.ACTION_DOWN -> true
                    else -> false
                }
                true
            }
    ) {
        Box(contentAlignment = Alignment.Center) {
            content()
        }
    }
}