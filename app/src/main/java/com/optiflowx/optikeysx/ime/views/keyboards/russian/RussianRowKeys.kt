package com.optiflowx.optikeysx.ime.views.keyboards.russian

import androidx.compose.runtime.Immutable
import com.optiflowx.optikeysx.core.model.Key

@Immutable
class RussianRowKeys {
    val row1Keys = arrayOf(
        Key("q", "й"),
        Key("w", "ц"),
        Key("e", "у"),
        Key("r", "к"),
        Key("t", "е"),
        Key("y", "н"),
        Key("u", "г"),
        Key("i", "ш"),
        Key("o", "щ"),
        Key("p", "з")
    )

    val row2Keys = arrayOf(
        Key("a", "ф"),
        Key("s", "ы"),
        Key("d", "в"),
        Key("f", "а"),
        Key("g", "п"),
        Key("h", "р"),
        Key("j", "о"),
        Key("k", "л"),
        Key("l", "д"),
        Key("ж","ж"),
    )

    val row3Keys = arrayOf(
        Key("shift", ""),
        Key("z", "я"),
        Key("x", "ч"),
        Key("c", "с"),
        Key("v", "м"),
        Key("b", "и"),
        Key("n", "т"),
        Key("m", "ь"),
        Key("б", "б"),
        Key("delete", "")
    )
}