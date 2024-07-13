package com.optiflowx.optikeysx.screens.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.optiflowx.optikeysx.R
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.SectionLink
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
        title = {
            CupertinoText(
                stringResource(R.string.developer_section),
                style = titleTextStyle
            )
        },
        caption = {
            CupertinoText(
                text = stringResource(R.string.developer_section_description),
                style = descTextStyle
            )
        }
    ) {
        SectionLink(
            title = {
                CupertinoText(
                    text = stringResource(R.string.copyright_information),
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
            title = {
                CupertinoText(
                    text = stringResource(R.string.join_support_channel),
                    color = CupertinoColors.systemGreen,
                    style = tileTextStyle
                )
            },
            onClick = { uriHandler.openUri("https://t.me/optiflowxparadise/") }
        )
    }
}