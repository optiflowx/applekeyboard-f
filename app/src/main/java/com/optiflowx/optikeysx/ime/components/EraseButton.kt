

package com.optiflowx.optikeysx.ime.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun EraseButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color,
    id: String,
    applyShadow: Boolean = true,
    onClick: () -> Unit,
    onRepeatableClick: () -> Unit,
    content: @Composable (Boolean) -> Unit,
) {
    val delayMillis = 115L
    var pressed by remember { mutableStateOf(false) }
    var pressedCount by remember { mutableIntStateOf(0) }
    val currentClickListener by rememberUpdatedState(onRepeatableClick)
    val currentSingleClickListener by rememberUpdatedState(onClick)

    LaunchedEffect(pressed) {
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

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .layoutId(id)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressed = true

                        if(tryAwaitRelease()) {
                            pressed = false
                        }
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        pressed = false
                    },
                    onDragCancel = {
                        pressed = false
                    }
                ) { _, _ -> }
            }
    ) {
        if(applyShadow) {
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
            color = if(pressed) MaterialTheme.colorScheme.surface else color,
            shape = RoundedCornerShape((5.5).dp),
            modifier = modifier.matchParentSize()
        ) {
            Box(contentAlignment = Alignment.Center) {
                content(pressed)
            }
        }
    }
}