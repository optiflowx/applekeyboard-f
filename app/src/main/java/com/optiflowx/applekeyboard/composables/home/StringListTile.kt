package com.optiflowx.applekeyboard.composables.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.ui.cupertinoBlue1
import com.optiflowx.applekeyboard.ui.regular
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.Surface
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray

@Composable
fun StringListTile(text: String, data: String?, entries: List<String>, onClick: (String) -> Unit) {
    Surface(
        color = Color.Transparent, modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Column(modifier = Modifier
            .padding(vertical = 7.dp, horizontal = 15.dp)
            .fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CupertinoText(
                    text = text,
                    color = Color.White,
                    fontFamily = regular,
                    fontSize = TextUnit(16f, TextUnitType.Sp)
                )

                CupertinoText(
                    text = "$data",
                    fontFamily = regular,
                    color = CupertinoColors.systemGray(isSystemInDarkTheme()),
                    fontSize = TextUnit(16f, TextUnitType.Sp)
                )
            }

            Spacer(Modifier.height(10.dp))

            //Selections
            LazyRow {
                for(value in entries) {
                    item (value) {
                        Surface(
                            color = if(data == value) cupertinoBlue1 else Color.DarkGray,
                            shape = RoundedCornerShape(3.dp),
                            modifier = Modifier.padding(2.dp),
                            onClick = { onClick(value) }
                        ) {
                            CupertinoText(
                                text = value,
                                color = Color.White,
                                fontFamily = regular,
                                modifier = Modifier.padding(3.dp),
                                fontSize = TextUnit(17f, TextUnitType.Sp)
                            )
                        }
                    }
                }
            }
        }
    }
}