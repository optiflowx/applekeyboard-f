package com.optiflowx.optikeysx.views.number

import androidx.compose.runtime.Immutable
import com.optiflowx.optikeysx.core.model.Key


@Immutable
class NumberRowKeys {
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

    val row3Keys = listOf(
        Key("7", "PQRS"),
        Key("8", "TUV"),
        Key("9", "WXYZ"),
    )

    val row4Keys = listOf(
        Key("period", ""),
        Key("0", ""),
        Key("delete", ""),
    )
}