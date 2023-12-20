package com.optiflowx.applekeyboard.ui

import androidx.compose.material.Typography
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
    defaultFontFamily = FontFamily(Font(R.font.regular, FontWeight.Normal)),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        fontFamily = defaultFontFamily,
    ),
    button = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        fontFamily = defaultFontFamily,
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        fontFamily = defaultFontFamily,
    ),
    h1 = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 30.sp,
        fontFamily = defaultFontFamily,
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 24.sp,
        fontFamily = defaultFontFamily,
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
        fontFamily = defaultFontFamily,
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 18.sp,
        fontFamily = defaultFontFamily,
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        fontFamily = defaultFontFamily,
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        fontFamily = defaultFontFamily,
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        fontFamily = defaultFontFamily,
    ),
)