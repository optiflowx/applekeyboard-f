package com.optiflowx.optikeysx.screens

import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.optiflowx.optikeysx.core.enums.KeyboardFontType
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.optikeysxPreferences
import com.optiflowx.optikeysx.ui.bold
import com.optiflowx.optikeysx.ui.regular
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoNavigateBackButton
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.CheckmarkCircle
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.CupertinoSectionDefaults
import io.github.alexzhirkevich.cupertino.section.LocalSectionStyle
import io.github.alexzhirkevich.cupertino.section.SectionItem
import io.github.alexzhirkevich.cupertino.section.SectionLink
import io.github.alexzhirkevich.cupertino.section.link
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue

class KeyboardFontsScreen : Screen {
    val prefs by optikeysxPreferences()

    @OptIn(ExperimentalCupertinoApi::class)
    @Composable
    override fun Content() {
        val fonts = KeyboardFontType.entries
        val navigator = LocalNavigator.currentOrThrow

        val currentFontType = prefs.keyboardFontType.observeAsState().value

        val tileTextStyle = TextStyle(
            fontSize = TextUnit(17f, TextUnitType.Sp).nonScaledSp,
            fontFamily = regular,
        )

        CupertinoScaffold(
            containerColor = CupertinoSectionDefaults.containerColor(LocalSectionStyle.current),
            topBar = {
                CupertinoTopAppBar(
                    navigationIcon = {
                        CupertinoNavigateBackButton(onClick = { navigator.pop() }) {
                            CupertinoText("Home")
                        }
                    },
                    title = {
                        CupertinoText(
                            text = "Keyboard Fonts",
                            fontFamily = bold,
                        )
                    }
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .statusBarsPadding()
                    .absolutePadding(top = 40.dp),
                userScrollEnabled = true
            ) {

                item("Keyboard Fonts") {
                    CupertinoSection {
                        fonts.forEachIndexed { index, font ->
                            this.SectionLink(
                                onClick = { prefs.keyboardFontType.set(font) },
                                title = { CupertinoText(font.name, style = tileTextStyle) },
                                chevron = {
                                    if (font == currentFontType) {
                                        CupertinoIcon(
                                            imageVector = CupertinoIcons.Default.CheckmarkCircle,
                                            contentDescription = "check",
                                            tint = CupertinoColors.systemBlue,
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}