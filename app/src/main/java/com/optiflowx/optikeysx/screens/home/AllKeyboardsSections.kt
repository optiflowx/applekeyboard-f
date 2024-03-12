package com.optiflowx.optikeysx.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.optiflowx.optikeysx.optikeysxPreferences
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.switch
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemRed

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun AllKeyboardsSection(
    titleTextStyle: TextStyle,
    descTextStyle: TextStyle,
    tileTextStyle: TextStyle,
    isPremium: Boolean,
) {
    val prefs by optikeysxPreferences()
    val isAutoCapitalization = prefs.isAutoCapitalisation.observeAsState().value
    val isAutoCorrect = prefs.isAutoCorrect.observeAsState().value
    val isCheckSpelling = prefs.isCheckSpelling.observeAsState().value
    val isEnableCapsLock = prefs.isEnableCapsLock.observeAsState().value
    val isPredictive = prefs.isPredictive.observeAsState().value
    val isCharacterPreview = prefs.isCharacterPreview.observeAsState().value
    val isDotShortcut = prefs.isDotShortcut.observeAsState().value
    val isEnableAccents = prefs.isEnableAccents.observeAsState().value
    val isEnableMemoji = prefs.isEnableMemoji.observeAsState().value


    CupertinoSection(
        title = { CupertinoText("ALL KEYBOARDS", style = titleTextStyle) },
        caption = {
            CupertinoText(
                text = "Double tapping the space bar will insert a full stop followed by a space.",
                style = descTextStyle
            )
        }
    ) {
        this.switch(
            title = {
                CupertinoText(
                    "Auto-Capitalisation", style = tileTextStyle,
                )
            },
            checked = isAutoCapitalization,
            onCheckedChange = { prefs.isAutoCapitalisation.set(it) }
        )
        this.switch(
            title = {
                CupertinoText(
                    text = "Auto-Correction",
                    color = CupertinoColors.systemRed,
                    style = tileTextStyle
                )
            },
            enabled = false,
            checked = isAutoCorrect,
            onCheckedChange = { prefs.isAutoCorrect.set(it) }
        )
        this.switch(
            title = {
                CupertinoText(
                    text = "Check Spelling",
                    color = CupertinoColors.systemRed,
                    style = tileTextStyle
                )
            },
            enabled = false,
            checked = isCheckSpelling,
            onCheckedChange = { prefs.isCheckSpelling.set(it) }
        )
        this.switch(
            title = {
                CupertinoText(
                    "Enable Caps Lock", style = tileTextStyle,
                )
            },
            checked = isEnableCapsLock,
            onCheckedChange = { prefs.isEnableCapsLock.set(it) }
        )
        this.switch(
            title = {
                CupertinoText(
                    "Predictive", style = tileTextStyle,
                    color = if (isPremium) Color.Unspecified else Color.Gray,
                )
            },
            enabled = isPremium,
            checked = isPredictive,
            onCheckedChange = { prefs.isPredictive.set(it) }
        )
        this.switch(
            title = {
                CupertinoText(
                    "Enable Memoji",
                    style = tileTextStyle,
                    color = CupertinoColors.systemRed,
                )
            },
            enabled = false,
            checked = isEnableMemoji,
            onCheckedChange = { prefs.isEnableMemoji.set(it) }
        )
        this.switch(
            title = {
                CupertinoText(
                    "Enable Accents",
                    color = CupertinoColors.systemRed,
                    style = tileTextStyle
                )
            },
            enabled = false,
            checked = isEnableAccents,
            onCheckedChange = { prefs.isEnableAccents.set(it) }
        )
        this.switch(
            title = {
                CupertinoText(
                    text = "Character Preview",
                    color = CupertinoColors.systemRed,
                    style = tileTextStyle,
                )
            },
            enabled = false,
            checked = isCharacterPreview,
            onCheckedChange = { prefs.isCharacterPreview.set(it) }
        )
        this.switch(
            title = {
                CupertinoText(
                    "\".\" Shortcut", style = tileTextStyle,
                    color = if (isPremium) Color.Unspecified else Color.Gray
                )
            },
            checked = isDotShortcut,
            enabled = isPremium,
            onCheckedChange = { prefs.isDotShortcut.set(it) }
        )
    }
}