package com.optiflowx.applekeyboard.utils

import androidx.compose.ui.text.font.FontFamily
import com.optiflowx.applekeyboard.ui.bold
import com.optiflowx.applekeyboard.ui.medium
import com.optiflowx.applekeyboard.ui.regular

fun appFontType(fontType: String?): FontFamily {
    return when(fontType) {
        KeyboardFontType.Bold.name -> bold
        KeyboardFontType.Medium.name -> medium
        KeyboardFontType.Regular.name -> regular
        else -> regular
    }
}