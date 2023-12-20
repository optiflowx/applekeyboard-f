package com.optiflowx.applekeyboard.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val Dark = darkColors(
    primary = Color.White, //Text Color on Text Key
    primaryVariant = Color.White, //Text Color on Action Key
    secondary = Color(0xff6B6B6B), //TKey Background Color
    secondaryVariant = Color(0xff474747), //Icon Key Background Color
    background = Color(0xff2C2C2C), //Keyboard Color
    surface = Color.White, //Shift Key Background Color
    onSurface = Color(0xFF007AFF), //Text Action Key Background Color
//    onPrimary = Color.Red,
//    onSecondary = Color.Blue,
    onBackground = Color.White,
)


@SuppressLint("ConflictingOnColor")
private val Light = lightColors(
    primary = Color.Black, //Text Color on Text Key
    primaryVariant = Color.White, //Text Color on Action Key
    secondary = Color.White, //TKey Background Color
    secondaryVariant = Color(0xffAEB3BE), //Special Key Background Color
    background = Color(0xffD4D6DC), //Keyboard Color
    surface = Color(0xCCFFFFFF), //Shift Key Background Color
    onSurface = Color(0xFF007AFF), //Text Action Key Background Color
//    onPrimary = Color.Blue,
//    onSecondary = Color.Red,
    onBackground = Color.Black,
)

@Composable
fun AppleKeyboardIMETheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) Dark else Light,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
