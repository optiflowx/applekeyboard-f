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
import io.github.alexzhirkevich.cupertino.section.link

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun GeneralSection(
    titleTextStyle: TextStyle,
    tileTextStyle: TextStyle,
    isPremium: Boolean,
) {
    val navigator = LocalNavigator.currentOrThrow

    CupertinoSection(
        title = { CupertinoText("GENERAL", style = titleTextStyle) },
    ) {
        this.link(
            key = 0,
            onClickLabel = "Keyboards",
            title = {
                CupertinoText(
                    "Keyboards", style = tileTextStyle,
                )
            },
            onClick = { navigator.push(KeyboardsScreen()) }
        )
        this.link(
            key = 1,
            enabled = isPremium,
            onClickLabel = "Keyboard Fonts",
            title = { CupertinoText("Keyboard Fonts", style = tileTextStyle,
                color = if (isPremium) Color.Unspecified else Color.Gray,) },
            onClick = { navigator.push(KeyboardFontsScreen()) }
        )
        this.link(
            key = 3,
            enabled = isPremium,
            onClickLabel = "Voice Keyboard",
            title = { CupertinoText("Voice Keyboard", style = tileTextStyle,
                color = if (isPremium) Color.Unspecified else Color.Gray,) },
            onClick = { navigator.push(VoiceRecognitionSettingsScreen()) }
        )
//        this.link(
//            key = 4,
//            onClickLabel = "Text Replacement",
//            title = {
//                CupertinoText("Text Replacement", style = tileTextStyle)
//            },
//            onClick = {
//                navigator.push(TextReplacementScreen())
//            }
//        )
    }
}