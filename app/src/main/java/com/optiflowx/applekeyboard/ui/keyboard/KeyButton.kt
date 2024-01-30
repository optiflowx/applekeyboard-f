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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.popup
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.optiflowx.applekeyboard.ui.keyShape
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "",
    color: Color,
    id: String,
    popupWidth: Float = 0f,
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
            || id == "switch" || id == "period")

//    val interactionSource = remember { MutableInteractionSource() }
//    val indication = LocalIndication.current

    LaunchedEffect(pressed, enabled) {
        if (id == "delete") {
            while (enabled && pressed) {
                if (pressedCount < 1) {
                    pressedCount.inc()
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

    val isPop = showPopup && id != "delete" && id != "erase"

    Surface(
        color = color,
        shape = RoundedCornerShape((5.5).dp),
        modifier = modifier
            .layoutId(id)
            .fillMaxSize()
            .semantics(
                properties = {
                    if (isPop) this.popup()
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
            if (isPop) {
                Popup(
                    alignment = Alignment.Center,
//                    offset = IntOffset(0, 0),
                    properties = PopupProperties(
                        focusable = true,
                        dismissOnBackPress = false,
                        excludeFromSystemGesture = false,
                        dismissOnClickOutside = false,
                        clippingEnabled = true,
                        usePlatformDefaultWidth = true,
                    ),
                ) {
                    if (pressed) KeyButtonPopup(popupWidth.dp, text)
                }
            }
            content()
        }
    }
}