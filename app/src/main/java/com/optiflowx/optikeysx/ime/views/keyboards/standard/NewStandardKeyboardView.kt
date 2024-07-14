package com.optiflowx.optikeysx.ime.views.keyboards.standard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.optiflowx.optikeysx.core.enums.KeyboardFontType
import com.optiflowx.optikeysx.core.model.Key
import com.optiflowx.optikeysx.core.utils.appFontType
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ime.components.PopupKey
import io.github.alexzhirkevich.cupertino.Surface

@Composable
fun Gap() {
    Spacer(
        modifier = Modifier.height(10.dp)
    )
}

@Composable
fun KeyView(key: Key) {
    val sWidth = LocalConfiguration.current.screenWidthDp.dp

    val keyWidth: Dp = (sWidth.value * StandardRowKeys().percent).dp

//    val popupWidth: Dp = (sWidth.value * StandardRowKeys().percentPopup).dp

    val iconKeyWidth: Dp = (sWidth.value * StandardRowKeys().pSmall).dp

    val spaceKeyWidth: Dp = (sWidth.value * StandardRowKeys().pBig).dp

    val actionKeyWidth: Dp = (sWidth.value * StandardRowKeys().pMedium).dp

    val keyHeight: Dp = 42.dp

    val width =
        if (key.id == "shift" || key.id == "delete" || key.id == "123" || key.id == "emoji") iconKeyWidth else {
            when (key.id) {
                "action" -> actionKeyWidth
                "space" -> spaceKeyWidth
                else -> keyWidth
            }
        }

    Box(
        contentAlignment = Alignment.Center
    ) {


        if (key.id == "p") {
            PopupKey(keyWidth)
        }

        Surface(
            color = Color.Red,
            shape = RoundedCornerShape((5.5).dp),
            modifier = Modifier
                .height(keyHeight)
                .width(width)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = key.value,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    style = TextStyle(
                        fontFamily = appFontType(KeyboardFontType.Regular),
                        fontSize = (if (key.id == "123" || key.id == "ABC" || key.id == "action" || key.id == "space")
                            15.sp else 22.5.sp).nonScaledSp,
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,

    )
@Composable
fun NewStandardKeyboardView() {
    val sWidth = LocalConfiguration.current.screenWidthDp.dp
    val keyGap: Dp = (sWidth.value * StandardRowKeys().gapW).dp
    val keyWidth: Dp = (sWidth.value * StandardRowKeys().percent).dp

    val row1Width = (keyWidth * 10) + (keyGap * 9)
    val row2Width = (keyWidth * 9) + (keyGap * 8)
    val row3Width = (keyWidth * 7) + (keyGap * 6)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(sWidth)
            .height(500.dp)
    ) {
        Column(
            modifier = Modifier.width(sWidth),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.width(row1Width),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                StandardRowKeys().row1Keys.forEach { key ->
                    KeyView(key)
                }
            }

            Gap()

            Row(
                modifier = Modifier.width(row2Width),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StandardRowKeys().row2Keys.forEach { key ->
                    KeyView(key)
                }
            }

            Gap()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val first = StandardRowKeys().row3Keys[0]
                val last = StandardRowKeys().row3Keys[8]

                KeyView(first)

                Row(
                    modifier = Modifier.width(row3Width),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    var i = 1
                    while (i <= StandardRowKeys().row3Keys.lastIndex - 1) {
                        val key = StandardRowKeys().row3Keys[i]
                        KeyView(key)
                        i += 1
                    }
                }

                KeyView(last)
            }

            Gap()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StandardRowKeys().row4Keys.forEach { key ->
                    KeyView(key)
                }
            }
        }
    }
}


