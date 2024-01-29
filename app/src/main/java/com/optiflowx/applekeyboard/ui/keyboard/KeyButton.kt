package com.optiflowx.applekeyboard.ui.keyboard

import android.view.MotionEvent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
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
import com.optiflowx.applekeyboard.ui.keyShape
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "",
    color: Color,
//    buttonWidth: Dp,
    id: String,
    showPopup: Boolean,
    onSingleClick: () -> Unit,
    onRepeatableClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val delayMillis = 120L
    var pressed by remember { mutableStateOf(false) }
    var pressedCount by remember { mutableIntStateOf(0) }
    val currentClickListener by rememberUpdatedState(onRepeatableClick)
    val currentSingleClickListener by rememberUpdatedState(onSingleClick)

    val isIgnoreElevation = (id == "shift" || id == "delete" || id == "space"
            || id == "return" || id == "emoji" || id == "symbol"
            || id == "switch" || id == ".")

//    val interactionSource = remember { MutableInteractionSource() }
//    val indication = LocalIndication.current

    LaunchedEffect(pressed, enabled) {
        if (id == "delete") {
            while (enabled && pressed) {
                if (pressedCount < 1) {
                    pressedCount = +1
                    currentSingleClickListener()
                } else {
                    currentClickListener()
                    delay(delayMillis)
                }
            }
            pressedCount = 0
        } else if (enabled && pressed) {
            currentClickListener()
            onSingleClick()
        }
    }

    Surface(
        color = color,
        shape = RoundedCornerShape((5.5).dp),
        modifier = modifier
            .layoutId(id)
//            .width(buttonWidth)
            .fillMaxSize()
            .semantics(
                properties = {
                    if (showPopup) this.popup()
                    else this.role = Role.Button
                }
            )
            .pointerInteropFilter {
                pressed = when (it.action) {
                    MotionEvent.ACTION_DOWN -> true
                    else -> false
                }
                true
            }
            .shadow(
                clip = true,
                elevation = if (isIgnoreElevation) 0.dp else (1.6).dp,
                shape = keyShape.small,
                ambientColor = Color.Transparent,
            )
    ) {
        Box(contentAlignment = Alignment.Center) {
//            if (showPopup) {
//                Popup(
//                    alignment = Alignment.BottomCenter,
//                    offset = IntOffset(0, 0),
//                    properties = PopupProperties(
//                        focusable = false,
//                        dismissOnBackPress = false,
//                        dismissOnClickOutside = false,
//                        clippingEnabled = false,
//                        usePlatformDefaultWidth = false,
//                    ),
//                ) {
//                    if (pressed) KeyButtonPopup(width = buttonWidth, text = text)
//                }
//            }
            content()
        }
    }
}