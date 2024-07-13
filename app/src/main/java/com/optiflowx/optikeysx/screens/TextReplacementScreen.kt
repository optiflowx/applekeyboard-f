package com.optiflowx.optikeysx.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.optiflowx.optikeysx.ui.bold
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoNavigateBackButton
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Plus
import io.github.alexzhirkevich.cupertino.section.CupertinoSectionDefaults
import io.github.alexzhirkevich.cupertino.section.LocalSectionStyle

class TextReplacementScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalCupertinoApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        CupertinoScaffold(
            containerColor = CupertinoSectionDefaults.containerColor(LocalSectionStyle.current),
            topBar = {
                CupertinoTopAppBar(
                    modifier = Modifier.padding(end = 15.dp),
                    navigationIcon = {
                        CupertinoNavigateBackButton(onClick = { navigator.pop() }) {
                            CupertinoText("Home")
                        }
                    },
                    title = {
                        CupertinoText(
                            text = "Text Replacement",
                            fontFamily = bold,
                        )
                    },
                    actions = {
                        CupertinoIcon(
                            imageVector = CupertinoIcons.Default.Plus,
                            contentDescription = "add"
                        )
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .absolutePadding(top = 40.dp),
            ) {
//                CupertinoSection {
//                    fonts.forEachIndexed { index, font ->
//                        this.link(
//                            key = index,
//                            title = { CupertinoText(font.name, style = tileTextStyle) },
//                            trailingIcon = {
//                                if (font.name == fontType.value) {
//                                    CupertinoIcon(
//                                        imageVector = CupertinoIcons.Default.CheckmarkCircle,
//                                        contentDescription = "check",
//                                        tint = CupertinoColors.systemBlue,
//                                    )
//                                }
//                            },
//                            onClick = { }
//                        )
//                    }
//                }

            }
        }
//    navigator: DestinationsNavigator

    }
}