package com.optiflowx.optikeysx.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.optiflowx.optikeysx.optikeysxPreferences
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.CupertinoSwitch
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.SectionItem
import io.github.alexzhirkevich.cupertino.section.switch
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemRed

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun AllKeyboardsSection(
    titleTextStyle: TextStyle,
    descTextStyle: TextStyle,
    tileTextStyle: TextStyle,
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
        SectionItem(
            title = { CupertinoText("Auto-Capitalisation", style = tileTextStyle) },
            trailingContent = {
                CupertinoSwitch(
                    checked = isAutoCapitalization,
                    onCheckedChange = { prefs.isAutoCapitalisation.set(it) }
                )
            }
        )
        SectionItem(
            title = { CupertinoText("Auto-Correction", style = tileTextStyle) },
            trailingContent = {
                CupertinoSwitch(
                    enabled = false,
                    checked = isAutoCorrect,
                    onCheckedChange = { prefs.isAutoCorrect.set(it) }
                )
            }
        )
        SectionItem(
            title = {
                CupertinoText(
                    text = "Check Spelling",
                    color = CupertinoColors.systemRed,
                    style = tileTextStyle,
                )
            },
            trailingContent = {
                CupertinoSwitch(
                    enabled = false,
                    checked = isCheckSpelling,
                    onCheckedChange = { prefs.isCheckSpelling.set(it) }
                )
            }
        )
        SectionItem(
            title = { CupertinoText("Enable Caps Lock", style = tileTextStyle) },
            trailingContent = {
                CupertinoSwitch(
                    checked = isEnableCapsLock,
                    onCheckedChange = { prefs.isEnableCapsLock.set(it) }
                )
            }
        )
        SectionItem(
            title = { CupertinoText("Predictive", style = tileTextStyle) },
            trailingContent = {
                CupertinoSwitch(
                    checked = isPredictive,
                    onCheckedChange = { prefs.isPredictive.set(it) }
                )
            }
        )
        SectionItem(
            title = {
                CupertinoText(
                    "Enable Memoji",
                    style = tileTextStyle,
                    color = CupertinoColors.systemRed,
                )
            },
            trailingContent = {
                CupertinoSwitch(
                    enabled = false,
                    checked = isEnableMemoji,
                    onCheckedChange = { prefs.isEnableMemoji.set(it) }
                )
            }
        )
        SectionItem(
            title = {
                CupertinoText(
                    text = "Enable Accents",
                    color = CupertinoColors.systemRed, style = tileTextStyle,
                )
            },
            trailingContent = {
                CupertinoSwitch(
                    enabled = false,
                    checked = isEnableAccents,
                    onCheckedChange = { prefs.isEnableAccents.set(it) }
                )
            }
        )
        SectionItem(
            title = { CupertinoText("Character Preview", style = tileTextStyle) },
            trailingContent = {
                CupertinoSwitch(
                    checked = isCharacterPreview,
                    onCheckedChange = { prefs.isCharacterPreview.set(it) }
                )
            }
        )
        SectionItem(
            title = { CupertinoText("\".\" Shortcut", style = tileTextStyle) },
            trailingContent = {
                CupertinoSwitch(
                    checked = isDotShortcut,
                    onCheckedChange = { prefs.isDotShortcut.set(it) }
                )
            }
        )
    }
}