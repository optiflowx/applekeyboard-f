package com.optiflowx.applekeyboard.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val Dark = darkColorScheme(
    primary = Color.White, //Text Color on Text Key
    inversePrimary = Color.White, //Text Color on Action Key
    secondary = Color(0xff6B6B6B), //TKey Background Color
    secondaryContainer = Color(0xff464646), //Icon Key Background Color
    background = Color(0xff2B2B2B), //Keyboard Color
    surface = Color.White, //Shift Key Background Color
    onSurface = Color(0xFF007AFF), //Text Action Key Background Color
    onBackground = Color.White,
)


@SuppressLint("ConflictingOnColor")
private val Light = lightColorScheme(
    primary = Color.Black, //Text Color on Text Key
    inversePrimary = Color.White, //Text Color on Action Key
    secondary = Color.White, //TKey Background Color
    secondaryContainer = Color(0xffAEB3BE), //Special Key Background Color
    background = Color(0xffD4D6DC), //Keyboard Color
    surface = Color(0xCCFFFFFF), //Shift Key Background Color
    onSurface = Color(0xFF007AFF), //Text Action Key Background Color
    onBackground = Color.Black,
)

@Composable
fun AppleKeyboardIMETheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) Dark else Light,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
