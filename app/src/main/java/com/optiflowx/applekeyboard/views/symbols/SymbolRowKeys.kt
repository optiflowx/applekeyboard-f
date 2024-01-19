package com.optiflowx.applekeyboard.views.symbols

import com.optiflowx.applekeyboard.models.Key

class SymbolRowKeys {
    val row1Keys = arrayOf(
        Key("1", "1"),
        Key("2", "2"),
        Key("3", "3"),
        Key("4", "4"),
        Key("5", "5"),
        Key("6", "6"),
        Key("7", "7"),
        Key("8", "8"),
        Key("9", "9"),
        Key("0", "0")
    )

    val row1SymbolKeys = arrayOf(
        Key("1", "["),
        Key("2", "]"),
        Key("3", "{"),
        Key("4", "}"),
        Key("5", "#"),
        Key("6", "%"),
        Key("7", "^"),
        Key("8", "*"),
        Key("9", "+"),
        Key("0", "=")
    )

    val row2Keys = arrayOf(
        Key("-", "-"),
        Key("/", "/"),
        Key(":", ":"),
        Key(";", ";"),
        Key("(", "("),
        Key(")", ")"),
        Key("$", "$"),
        Key("&", "&"),
        Key("@", "@"),
        Key("\"", "\"")
    )

    val row2SymbolKeys = arrayOf(
        Key("-", "_"),
        Key("/", "\\"),
        Key(":", "|"),
        Key(";", "~"),
        Key("(", "<"),
        Key(")", ">"),
        Key("$", "€"),
        Key("&", "£"),
        Key("@", "˚"),
        Key("\"", "•")
    )

    val row3Keys = arrayOf(
        Key(".", "."),
        Key(",", ","),
        Key("?", "?"),
        Key("!", "!"),
        Key("'", "'")
    )
}