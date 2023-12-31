package com.optiflowx.applekeyboard.common

import androidx.compose.ui.text.font.FontFamily
import com.optiflowx.applekeyboard.ui.bold
import com.optiflowx.applekeyboard.ui.medium
import com.optiflowx.applekeyboard.ui.regular

fun appFontType(fontType: String?): FontFamily {
    return when(fontType) {
        "bold" -> bold
        "medium" -> medium
        else -> regular
    }
}