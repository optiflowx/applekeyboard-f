package com.optiflowx.applekeyboard.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontLoadingStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.optiflowx.applekeyboard.R

val boldFontFamily = FontFamily(Font(R.font.bold, FontWeight.Bold))
val mediumFontFamily = FontFamily(Font(R.font.medium, FontWeight.Medium))
val defaultFontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal))
val iosEmojiFontFamily = FontFamily(Font(R.font.iosemojifont, loadingStrategy = FontLoadingStrategy.Async))
// Set of Material typography styles to start with
val typography = Typography(
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        fontFamily = defaultFontFamily,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        fontFamily = defaultFontFamily,
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        fontFamily = defaultFontFamily,
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 30.sp,
        fontFamily = defaultFontFamily,
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 24.sp,
        fontFamily = defaultFontFamily,
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
        fontFamily = defaultFontFamily,
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        fontFamily = defaultFontFamily,
    ),
)