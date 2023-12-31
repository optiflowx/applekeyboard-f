package com.optiflowx.applekeyboard.composables.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.cupertino.CupertinoSwitch
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.Surface

@Composable
fun BooleanListTile(
    checked: Boolean? = null,
    text: String,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        color = Color.Transparent, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 14.dp)
        ) {
            CupertinoText(
                text = text,
                color = Color.White,
                fontSize = TextUnit(16f, TextUnitType.Sp)
            )

            if (checked != null) {
                CupertinoSwitch(
                    checked = checked,
                    onCheckedChange = onCheckedChange,
                    enabled = true
                )
            }
        }
    }
}