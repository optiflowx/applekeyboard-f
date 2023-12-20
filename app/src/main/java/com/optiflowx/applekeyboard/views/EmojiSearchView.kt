package com.optiflowx.applekeyboard.views

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.ui.defaultFontFamily
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoSearchTextField
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.MagnifyingGlass
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import io.github.alexzhirkevich.cupertino.theme.systemGray

@Preview
@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun EmojiSearchView() {

    CupertinoSearchTextField(
        value = "",
        placeholder = {
            Text(
                "Search Emoji",
                color = CupertinoColors.systemGray(isSystemInDarkTheme()),
                fontFamily = defaultFontFamily,
                fontSize = TextUnit(17f, TextUnitType.Sp),
            )
        },
        leadingIcon = {
            CupertinoIcon(
                CupertinoIcons.Outlined.MagnifyingGlass,
                contentDescription = "icon",
                tint = CupertinoColors.systemGray(isSystemInDarkTheme()),
                modifier = Modifier
                    .height(27.dp)
                    .fillMaxHeight(),)
        },
        modifier = Modifier
            .height(50.dp)
            .fillMaxHeight(),
        onValueChange = {
        },
    ) {
    }
}