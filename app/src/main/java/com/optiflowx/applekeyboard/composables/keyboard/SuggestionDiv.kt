package com.optiflowx.applekeyboard.composables.keyboard

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp

@Composable
fun Div(id: String) {
    Surface(
        color = Color(0xff757575),
        modifier = Modifier
            .layoutId(id)
            .width(1.dp)
            .fillMaxHeight()
            .padding(vertical = 9.dp)
    ) {}
}