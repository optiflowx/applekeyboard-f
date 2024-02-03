package com.optiflowx.optikeysx.ui.keyboard

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp

@Composable
fun Div(id: String) {
    Surface(
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .layoutId(id)
            .width(1.dp)
            .fillMaxHeight()
            .padding(vertical = 12.dp)
    ) {}
}