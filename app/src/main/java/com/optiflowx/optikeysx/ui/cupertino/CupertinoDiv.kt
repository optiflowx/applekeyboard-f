package com.optiflowx.optikeysx.ui.cupertino

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.cupertino.CupertinoDivider
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray7

@Composable
fun CupertinoDiv() {
    return CupertinoDivider(
        modifier = Modifier,
        thickness = 1.dp,
        color = CupertinoColors.systemGray7,
    )
}