package com.optiflowx.applekeyboard.state

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Stable
class KeyboardKeyState(
    defaultActionButtonColor: Color,
    defaultActionTextColor: Color,
    defaultActionText: String,
) {
    val actionButtonColor = mutableStateOf(defaultActionButtonColor)
    val actionTextColor = mutableStateOf(defaultActionTextColor)
    val actionText = mutableStateOf(defaultActionText)
}

@Composable
fun rememberKeyboardKeyState(
    defaultActionButtonColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    defaultActionTextColor: Color = MaterialTheme.colorScheme.inversePrimary,
    defaultActionText: String = "return",
) = remember(defaultActionButtonColor, defaultActionTextColor, defaultActionText) {
     KeyboardKeyState(
        defaultActionButtonColor,
        defaultActionTextColor,
        defaultActionText,
    )
}
