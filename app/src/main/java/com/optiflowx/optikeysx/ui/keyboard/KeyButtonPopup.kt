package com.optiflowx.optikeysx.ui.keyboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.core.enums.KeyboardFontType
import com.optiflowx.optikeysx.core.utils.appFontType
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ui.keyShapeValue

@Preview
@Composable
fun KeyButtonPopup(width: Dp = 52.dp, text: String = "M", id: String = "") {
    val height: Dp = 96.dp
    val previewWidth: Dp = ((width.value * 0.6) + width.value).dp

//    Popup(
//        alignment = Alignment.BottomCenter,
//        properties = PopupProperties(
//            focusable = false,
//            clippingEnabled = false,
//        )
//    ) {
    Column(
        modifier = Modifier.layoutId(id).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier =
    ) {
        Surface(
            color = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(
                topStart = 13.dp,
                topEnd = 13.dp,
                bottomStart = 15.dp,
                bottomEnd = 15.dp,
            ),
            modifier = Modifier
                .size(previewWidth, height * 0.54f)
                .shadow(7.5.dp)
        ) {
            Box(
                Modifier.fillMaxSize(), Alignment.Center
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontFamily = appFontType(KeyboardFontType.Regular),
                        platformStyle = PlatformTextStyle(includeFontPadding = false),
                        fontSize = TextUnit(24f, TextUnitType.Sp).nonScaledSp,
                    ),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        Surface(
            color = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = keyShapeValue,
                bottomEnd = keyShapeValue,
            ),
            modifier = Modifier.size(width, height * 0.46f)
        ) {}
    }
//    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun AppleKeyPreview() {
//    Canvas(
//        modifier = Modifier
//            .height(120.dp).width(65.dp)
////            .border(width = 2.dp, color = Color.Magenta, shape = RectangleShape)
//    ) {
//        val w = size.width
//        val h = size.height
//
//        val previewKey = Path().apply {
//            moveTo(w * 0f, 0f)
//            lineTo(x = 0f, y = h * 0.45f)
//            quadraticBezierTo(
//                x1 = 0f, y1 = h * 0.5f,
//                x2 = w * 0.115f, y2 = h * 0.54f
//            )
//            quadraticBezierTo(
//                x1 = w * 0.15f, y1 = h * 0.56f,
//                x2 = w * 0.165f, y2 = h * 0.6f
//            )
//            lineTo(x = w * 0.16f, y = h)
//            quadraticBezierTo(
//                x1 = w * 0.16f, y1 = h,
//                x2 = w * 0.20f, y2 = h,
//            )
//            lineTo(x = w * 0.84f, y = h)
//            lineTo(x = w * 0.84f, y = h * 0.6f)
//            quadraticBezierTo(
//                x1 = w - (w * 0.15f), y1 = h * 0.56f,
//                x2 = w - (w * 0.125f), y2 = h * 0.54f
//            )
//            quadraticBezierTo(
//                x1 = w, y1 = h * 0.5f,
//                x2 = w, y2 = h * 0.45f
//            )
//            lineTo(x = w, y = 0.45f)
//            close()
//        }
//
//        drawPath(
//            path = previewKey,
//            color = Color.Red,
//            style = androidx.compose.ui.graphics.drawscope.Stroke(
//                width = 2.dp.toPx()
//            )
//        )
//        clipPath()
//    }
//}