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
    secondary = Color(0xff6c6c6c), //TKey Background Color
    secondaryContainer = Color(0xff474747), //Icon Key Background Color
    background = Color(0xff2c2c2c), //Keyboard Color
    surface = Color(0xffd4d4d4), //Shift Key Background Color
    onSurface = Color(0xFF007AFF), //Text Action Key Background Color
    onBackground = Color.White,
    scrim = Color.White, //Bottom Icons color
    onPrimary = Color(0xFF404040), //Suggestion Div Color
    tertiaryContainer = Color(0xff1c1c1c), //Key Shadow Color
    onSurfaceVariant = Color(0xff6c6c6c), //Disabled Color
)


@SuppressLint("ConflictingOnColor")
private val Light = lightColorScheme(
    primary = Color.Black, //Text Color on Text Key
    inversePrimary = Color.White, //Text Color on Action Key
    secondary = Color.White, //TKey Background Color
    secondaryContainer = Color(0xffabafb8), //Special Key Background Color
    background = Color(0xffd2d3d8), //Keyboard Color
    surface = Color(0xFFFFFFFF), //Shift Key Background Color
    onSurface = Color(0xFF007AFF), //Text Action Key Background Color
    onBackground = Color.Black,
    scrim = Color(0xFF52555A), //Bottom Icons color
    onPrimary = Color(0xFFbcbdc0), //Suggestion Div Color
    tertiaryContainer = Color(0xff919095), //Key Shadow Color
    onSurfaceVariant = Color(0xff6c6c6c), //Disabled Color
)

@Composable
fun AppleKeyboardIMETheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) Dark else Light,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
