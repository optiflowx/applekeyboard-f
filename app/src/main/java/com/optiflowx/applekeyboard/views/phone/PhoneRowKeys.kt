package com.optiflowx.applekeyboard.views.phone

import androidx.compose.runtime.Immutable
import com.optiflowx.applekeyboard.models.Key
import com.optiflowx.applekeyboard.utils.KeyboardLocale

@Immutable
class PhoneRowKeys(locale: String?) {
    private val keyboardLocale = KeyboardLocale()

    val row1Keys = listOf(
        Key("1", ""),
        Key("2", "ABC"),
        Key("3", "DEF"),
    )

    val row2Keys = listOf(
        Key("4", "GHI"),
        Key("5", "JKL"),
        Key("6", "MNO"),
    )

    val row2KeysB = listOf(
        Key("4", keyboardLocale.pause(locale)),
        Key("5", "JKL"),
        Key("6", keyboardLocale.wait(locale)),
    )

    val row3Keys = listOf(
        Key("7", "PQRS"),
        Key("8", "TUV"),
        Key("9", "WXYZ"),
    )

    val row3KeysB = listOf(
        Key("7", "*"),
        Key("8", "TUV"),
        Key("9", "#"),
    )

    val row4Keys = listOf(
        Key("switch", ""),
        Key("0", ""),
        Key("erase", ""),
    )

    val row4keysB = listOf(
        Key("switch", ""),
        Key("0", "+"),
        Key("erase", ""),
    )
}