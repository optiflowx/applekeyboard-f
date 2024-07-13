package com.optiflowx.optikeysx.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.optiflowx.optikeysx.screens.KeyboardFontsScreen
import com.optiflowx.optikeysx.screens.KeyboardsScreen
import com.optiflowx.optikeysx.screens.VoiceRecognitionSettingsScreen
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.SectionLink
import io.github.alexzhirkevich.cupertino.section.link

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun GeneralSection(
    titleTextStyle: TextStyle,
    tileTextStyle: TextStyle,
) {
    val navigator = LocalNavigator.currentOrThrow

    CupertinoSection(
        title = { CupertinoText("GENERAL", style = titleTextStyle) },
    ) {
        SectionLink(
            onClickLabel = "Keyboards",
            title = {
                CupertinoText(
                    "Keyboards", style = tileTextStyle,
                )
            },
            onClick = { navigator.push(KeyboardsScreen()) }
        )
        SectionLink(
            onClickLabel = "Keyboard Fonts",
            title = { CupertinoText("Keyboard Fonts", style = tileTextStyle) },
            onClick = { navigator.push(KeyboardFontsScreen()) }
        )
        SectionLink(
            onClickLabel = "Voice Keyboard",
            title = { CupertinoText("Voice Keyboard", style = tileTextStyle) },
            onClick = { navigator.push(VoiceRecognitionSettingsScreen()) }
        )
        SectionLink(
            onClickLabel = "Text Replacement",
            enabled = false,
            title = {
                CupertinoText(
                    "Text Replacement", style = tileTextStyle,
                    color = Color.Red
                )
            },
            onClick = {
//                navigator.push(TextReplacementScreen())
            }
        )
    }
}