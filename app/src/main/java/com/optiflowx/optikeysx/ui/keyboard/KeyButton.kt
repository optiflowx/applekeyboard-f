package com.optiflowx.optikeysx.ui.keyboard

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
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

@Composable
fun KeyButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    key: Key,
    color: Color,
    popupWidth: Float = 0f,
    showPopup: Boolean,
    prefs: AppPrefs,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val isIgnoreElevation = (key.id == "switch" || key.id == "period")

    val interactionSource = remember { MutableInteractionSource() }

    val isCharacterPreview = prefs.isCharacterPreview.observeAsState().value

    var isKeyPressed by remember { mutableStateOf(false) }

    val isEnableAccents = prefs.isEnableAccents.observeAsState().value

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
            ) {  }
        }

        Surface(
            color = color,
            shape = RoundedCornerShape((5.5).dp),
//            interactionSource = interactionSource,
            modifier = modifier
                .matchParentSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isKeyPressed = true

                            onClick()

                            if (tryAwaitRelease()) {
                                isKeyPressed = false
                            }
                        },
                    )
                }
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (isKeyPressed && showPopup && isEnableAccents) {
                    KeyAccentsPopup(popupWidth.dp, key)
                }

                //
//                if (pressed.value && showPopup && isCharacterPreview) {
//                    KeyButtonPopup(popupWidth.dp, key.value)
//                }

//                if (!isKeyPressed) {
                content()
//                }

            }
        }
    }
}

@Preview
@Composable
fun PopupKey() {
    val density = LocalDensity.current

    val height = 100.dp
    val width = 50.dp

    val viewHeight = with(density) { height.toPx() }
    val viewWidth = with(density) { width.toPx() }

    val cornerRadius = with(density) { 5.5.dp.toPx() }

    val topExpandableViewHeight = with(density) { 50.dp.toPx() }
    val bottomViewHeight = with(density) { 42.dp.toPx() }


    val tiltHeight = viewHeight - topExpandableViewHeight - bottomViewHeight
    val tiltWidth = viewWidth * 0.2.toFloat()
    val bottomLine = viewWidth * 0.8.toFloat()

    val tiltHeightInView = topExpandableViewHeight + tiltHeight


    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .drawBehind {
                val path = Path().apply {

                    moveTo(cornerRadius, 0f) //corner
                    this.quadraticBezierTo(
                        0f, 0f,
                        0f, cornerRadius,
                    )
                    lineTo(0f, topExpandableViewHeight) //corner
                    lineTo(tiltWidth, tiltHeightInView) // corner
                    lineTo(tiltWidth, viewHeight) //corner
                    lineTo(bottomLine, viewHeight) //corner
                    lineTo(bottomLine, viewHeight - bottomViewHeight) //corner
                    lineTo(viewWidth, viewHeight - bottomViewHeight - tiltHeight) //corner
                    lineTo(viewWidth, 0f) //corner

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