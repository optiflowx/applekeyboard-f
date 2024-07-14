package com.optiflowx.optikeysx.ime.views.keyboards.portuguese

import androidx.compose.runtime.Immutable
import com.optiflowx.optikeysx.core.model.Key

@Immutable
class PortugueseARowKeys {
    val row1Keys = arrayOf(
        Key("q", "q"),
        Key("w", "w"),
        Key("e", "e", listOf("é", "è", "ê", "ë", "ē", "ė", "ę")),
        Key("r", "r"),
        Key("t", "t"),
        Key("y", "y", listOf("ý", "ÿ")),
        Key("u", "u", listOf("ú", "ù", "û", "ü", "ū", "ų")),
        Key("i", "i", listOf("í", "ì", "î", "ï", "ī", "į", "ı")),
        Key("o", "o", listOf("ó", "ò", "ô", "ö", "ø", "õ", "ō", "œ")),
        Key("p", "p")
    )

    val row2Keys = arrayOf(
        Key("a", "a", listOf("á", "à", "â", "ä", "æ", "ã", "å", "ā")),
        Key("s", "s", listOf("š", "ś", "ş", "ŝ", "š")),
        Key("d", "d"),
        Key("f", "f"),
        Key("g", "g"),
        Key("h", "h"),
        Key("j", "j"),
        Key("k", "k"),
        Key("l", "l"),
        Key("ç", "ç"),
    )

    val row3Keys = arrayOf(
        Key("shift", ""),
        Key("z", "z", listOf("ž", "ź", "ż")),
        Key("x", "x"),
        Key("c","c", listOf("ç")),
        Key("v", "v"),
        Key("b", "b"),
        Key("n", "n", listOf("ñ", "ņ", "ň")),
        Key("m", "m"),
        Key("delete", "")
    )
}