package com.optiflowx.optikeysx.ui.keyboard

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.AppPrefs
import com.optiflowx.optikeysx.core.model.Key
import dev.patrickgold.jetpref.datastore.model.observeAsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun KeyButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    key: Key,
    popupText: String = "Y",
    color: Color,
    popupWidth: Float = 0f,
    showPopup: Boolean,
    prefs: AppPrefs,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val TAG = "KEY BUTTON"
    val isIgnoreElevation = (key.id == "switch" || key.id == "period")

//    val interactionSource = remember { MutableInteractionSource() }

    val isCharacterPreview = prefs.isCharacterPreview.observeAsState().value

    var isShowPopup by remember { mutableStateOf(false) }

//    var isShowAccents by remember { mutableStateOf(false) }
//
//    val isEnableAccents = prefs.isEnableAccents.observeAsState().value

    val scope = rememberCoroutineScope()

    fun resetPopup() {
        scope.launch(Dispatchers.IO) {
            delay(80).apply {
                isShowPopup = false
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .layoutId(key.id)
            .fillMaxSize()
            .graphicsLayer(clip = false)
    ) {
        if (!isIgnoreElevation) {
            Surface(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(5.5.dp),
                modifier = Modifier
                    .matchParentSize()
                    .absoluteOffset(0.dp, 1.5.dp)
                    .absolutePadding(left = 0.75.dp, right = 0.5.dp)
            ) { }
        }

        Surface(
            color = color,
            shape = RoundedCornerShape((5.5).dp),
            modifier = modifier
                .matchParentSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isShowPopup = true

                            onClick()

                            if (tryAwaitRelease()) resetPopup()
                        }
                    )
                }
                .pointerInput(Unit) {
                    this.detectDragGestures(
                        onDragEnd = { resetPopup() },
                        onDragCancel = { resetPopup() },
                    ) { change, dragAmount ->
                        if(!isShowPopup) {
                            isShowPopup = change.pressed
                        }
                        //Check if the key Press state is still true
                        Log.d(TAG, "Is drag consumed: ${change.isConsumed}")

                        //Set the global state of the drag gestures to the change and dragAmount
                        //use these values in a launched effect of the buttons and show the accents selection.
                        Log.d(
                            TAG,
                            "Change Scroll Delta: x = ${change.scrollDelta.x} , y = ${change.scrollDelta.y}"
                        )
                        Log.d(TAG, "Change Position: ${change.position}")
                        Log.d(TAG, "Drag Amount(x,y): $dragAmount")
                    }
                }
        ) {
            Box(contentAlignment = Alignment.Center) {
//                if (isKeyPressed && showPopup && isEnableAccents) {
//                    KeyAccentsPopup(popupWidth.dp, key)
//                }

                if (isShowPopup && showPopup && isCharacterPreview) {
                    PopupKey(popupWidth.dp, popupText, key)
                } else {
                    content()
                }
            }
        }
    }
}

@Preview
@Composable
fun PopupKeyTest() {
    val density = LocalDensity.current

    val height = 105.dp
    val width = 50.dp

    val viewHeight = with(density) { height.toPx() }
    val viewWidth = with(density) { width.toPx() * 0.85f }

    val cornerRadius = with(density) { 5.5.dp.toPx() }
    val radiusTop = with(density) { 8.dp.toPx() }

    val topExpandableViewHeight = with(density) { 48.dp.toPx() }
    val bottomViewHeight = with(density) { 42.dp.toPx() }


    val tiltHeight = viewHeight - topExpandableViewHeight - bottomViewHeight
    val tiltWidth = viewWidth * 0.15.toFloat()
    val bottomLine = viewWidth + tiltWidth - 3
    val tiltHeightInView = topExpandableViewHeight + tiltHeight

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(height)
            .width(width)
            .drawBehind {
                val path = Path().apply {
                    moveTo(radiusTop, 0f) //corner
                    this.quadraticBezierTo(
                        0f, 0f,
                        0f, radiusTop,
                    )
                    lineTo(0f, topExpandableViewHeight) //corner
                    lineTo(tiltWidth * 2, tiltHeightInView) // corner
                    lineTo(tiltWidth * 2, viewHeight - cornerRadius) //corner
                    this.quadraticBezierTo(
                        tiltWidth * 2, viewHeight,
                        tiltWidth * 2 + cornerRadius, viewHeight,
                    )
                    lineTo(bottomLine - cornerRadius, viewHeight) //corner
                    this.quadraticBezierTo(
                        bottomLine, viewHeight,
                        bottomLine, viewHeight - cornerRadius,
                    )
                    lineTo(bottomLine, viewHeight - bottomViewHeight) //corner
                    lineTo(bottomLine, radiusTop) //corner
                    this.quadraticBezierTo(
                        bottomLine, 0f,
                        bottomLine - radiusTop, 0f,
                    )

                    close()
                }

                drawPath(
                    path = path,
                    color = Color.Red,
                )
            }
    ) {
    }
}