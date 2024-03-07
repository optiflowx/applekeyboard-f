package com.optiflowx.optikeysx.ui.keyboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.optiflowx.optikeysx.core.enums.KeyboardFontType
import com.optiflowx.optikeysx.core.model.Key
import com.optiflowx.optikeysx.core.utils.appFontType
import com.optiflowx.optikeysx.core.utils.boxShadow
import com.optiflowx.optikeysx.core.utils.nonScaledSp

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun KeyAccentsPopup(previewWidth: Dp = 52.dp, key: Key = Key("s", "", listOf("a", "b"))) {
    val height: Dp = 52.dp

    Popup(
        alignment = Alignment.BottomCenter,
        offset = IntOffset(0, -(height * 2).value.toInt()),
        properties = PopupProperties(
            focusable = false,
            usePlatformDefaultWidth = true,
        )
    ) {
        Surface(
            color = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = 12.dp,
                bottomEnd = 12.dp,
            ),
            modifier = Modifier
                .height(height)
                .boxShadow(
                    offsetY = (-10).dp,
                    borderRadius = 6.dp,
                )
                .padding(horizontal = 3.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 3.dp,
                        vertical = 4.dp,
                    )
            ) {
                for (text in key.accents) {
                    Surface(
                        color = Color.Red,
                        shape = RoundedCornerShape(5.5.dp),
                        modifier = Modifier.width(previewWidth)
                            .padding(horizontal = 1.8.dp)
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
                                    fontSize = 24.sp.nonScaledSp,
                                ),
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }
            }
        }
    }
}
