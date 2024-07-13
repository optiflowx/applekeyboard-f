package com.optiflowx.optikeysx.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.SectionLink
import io.github.alexzhirkevich.cupertino.section.link
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue
import io.github.alexzhirkevich.cupertino.theme.systemGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun DeveloperSection(
    titleTextStyle: TextStyle,
    descTextStyle: TextStyle,
    tileTextStyle: TextStyle,
    scaffoldState: CupertinoBottomSheetScaffoldState,
) {
    val coroutineScope = rememberCoroutineScope()
    val uriHandler = LocalUriHandler.current


    CupertinoSection(
        title = { CupertinoText("DEVELOPER", style = titleTextStyle) },
        caption = {
            CupertinoText(
                text = "This section contains important information from the developer.",
                style = descTextStyle
            )
        }
    ) {
        SectionLink(
            onClickLabel = "Copyright Information",
            title = {
                CupertinoText(
                    text = "Copyright Information",
                    color = CupertinoColors.systemBlue,
                    style = tileTextStyle
                )
            },
            onClick = {
                coroutineScope.launch {
                    scaffoldState.bottomSheetState.show()
                }
            }
        )
        SectionLink(
            onClickLabel = "Join The Support Channel",
            title = {
                CupertinoText(
                    text = "Join The Support Channel",
                    color = CupertinoColors.systemGreen,
                    style = tileTextStyle
                )
            },
            onClick = { uriHandler.openUri("https://t.me/optiflowxparadise/") }
        )
    }
}