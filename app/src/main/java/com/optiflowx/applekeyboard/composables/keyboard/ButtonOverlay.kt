package com.optiflowx.applekeyboard.composables.keyboard

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ButtonOverlay(width: Dp = 30.dp, height: Dp = 42.dp, text: String = "M") {
    val size = ((width.value * 0.4) + width.value).dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.size(size, size)
        ) {
            Box(
                Modifier.fillMaxSize(), Alignment.Center
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
//                    fontFamily = appFontType(fontType),
                    fontSize = TextUnit(22f, TextUnitType.Sp),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Surface(
            color = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 5.dp,
                bottomEnd = 5.dp,
            ),
            modifier = Modifier.size(width, height)
        ) {}
    }
}