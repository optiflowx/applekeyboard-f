package com.optiflowx.optikeysx.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.optiflowx.optikeysx.core.enums.KeyboardFontType
import com.optiflowx.optikeysx.core.model.Key
import com.optiflowx.optikeysx.core.utils.appFontType
import com.optiflowx.optikeysx.core.utils.boxShadow
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ui.PopupShape
import com.optiflowx.optikeysx.ui.PopupShapeLeft
import com.optiflowx.optikeysx.ui.PopupShapeRight

@Preview
@Composable
fun PopupKey(inWidth: Dp = 50.dp, text: String = "p", key: Key = Key("p", "p")) {
    val height = 105.dp
    val width = inWidth + (inWidth * 0.5f)
    val colorScheme = MaterialTheme.colorScheme

    //Sides from which the the button is pressed
    val isRight = (key.id == "p" || key.id == "0" || key.id == "\"")
    val isLeft = (key.id == "q" || key.id == "1" || key.id == "-")

    //Shapes
    val leftShape = PopupShapeLeft(inWidth)
    val regularShape = PopupShape(inWidth)
    val rightShape = PopupShapeRight(inWidth)

    //Offsets
    val regularOffset = DpOffset(0.dp,8.dp)
    val leftOffset = DpOffset((-6).dp, 8.dp)
    val rightOffset = DpOffset(0.dp, 8.dp)

    //Definition of conditional data.
    val shape = if(isLeft) leftShape else if (isRight) rightShape else regularShape
    val offset = if(isLeft) leftOffset else if(isRight) rightOffset else regularOffset

    Popup(
        alignment = Alignment.BottomCenter,
        properties = PopupProperties(
            focusable = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Box(
            modifier = Modifier
                .height(height)
                .width(width)
                .boxShadow(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    alpha = 0.5f,
                    offsetX = 0.dp,
                    offsetY = 0.dp,
                    blurRadius = 14.dp
                )
                .clip(shape)
                .background(colorScheme.secondary)
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(offset.x, offset.y),
                style = TextStyle(
                    color = colorScheme.primary,
                    fontFamily = appFontType(KeyboardFontType.Regular),
                    fontSize = 28.sp.nonScaledSp,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
            )
        }
    }
}
