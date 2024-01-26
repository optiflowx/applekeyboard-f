package com.optiflowx.applekeyboard.views.normal.rowkeys

import androidx.compose.runtime.Immutable
import com.optiflowx.applekeyboard.core.data.Key

@Immutable
class FrenchRowKeys {
    val row1Keys = arrayOf(
        Key("a", "a"),
        Key("z", "z"),
        Key("e", "e"),
        Key("r", "r"),
        Key("t", "t"),
        Key("y", "y"),
        Key("u", "u"),
        Key("i", "i"),
        Key("o", "o"),
        Key("p", "p")
    )

    val row2Keys = arrayOf(
        Key("q", "q"),
        Key("s", "s"),
        Key("d", "d"),
        Key("f", "f"),
        Key("g", "g"),
        Key("h", "h"),
        Key("j", "j"),
        Key("k", "k"),
        Key("l", "l"),
    )

    val row3Keys = arrayOf(
        Key("shift", ""),
        Key("z", "z"),
        Key("x", "x"),
        Key("c", "c"),
        Key("v", "v"),
        Key("b", "b"),
        Key("n", "n"),
        Key("m", "m"),
        Key("erase", "")
    )
}