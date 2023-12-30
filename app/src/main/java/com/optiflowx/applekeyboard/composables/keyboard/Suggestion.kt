package com.optiflowx.applekeyboard.composables.keyboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.ui.defaultFontFamily

@Composable
fun Suggestion(id: String, suggestion: String, onClick: () -> Unit) {
    val keyboardWidth = LocalConfiguration.current.screenWidthDp
    val width: Dp = (keyboardWidth * 0.31).dp

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(6.dp),
        color = Color.Transparent,
        modifier = Modifier
            .layoutId(id)
            .width(width)
            .fillMaxHeight()
            .padding(vertical = 3.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = suggestion,
                textAlign = TextAlign.Center,
                maxLines = 1,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f),
                    fontSize = TextUnit(16f, TextUnitType.Sp),
                    fontFamily = defaultFontFamily,
                ),
            )
        }
    }
}