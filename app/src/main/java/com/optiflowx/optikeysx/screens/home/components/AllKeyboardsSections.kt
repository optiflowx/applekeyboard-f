package com.optiflowx.optikeysx.screens.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.optikeysxPreferences
import com.optiflowx.optikeysx.viewmodels.KeyboardSettingsModel
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.CupertinoSwitch
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.SectionItem
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemOrange
import io.github.alexzhirkevich.cupertino.theme.systemRed


@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun AllKeyboardsSection(
    titleTextStyle: TextStyle,
    descTextStyle: TextStyle,
    tileTextStyle: TextStyle,
    showSpeechAlertDialog: MutableState<Boolean>,
    settingsModel: KeyboardSettingsModel,
) {
    val prefs by optikeysxPreferences()
//    val context = LocalContext.current
    val isAutoCapitalization = prefs.isAutoCapitalisation.observeAsState().value
//    val isAutoCorrect = prefs.isAutoCorrect.observeAsState().value
//    val isCheckSpelling = prefs.isCheckSpelling.observeAsState().value
    val isEnableCapsLock = prefs.isEnableCapsLock.observeAsState().value
    val isPredictive = prefs.isPredictive.observeAsState().value
    val isCharacterPreview = prefs.isCharacterPreview.observeAsState().value
    val isDotShortcut = prefs.isDotShortcut.observeAsState().value
    val isEnableAccents = prefs.isEnableAccents.observeAsState().value
    val isEnableMemoji = prefs.isEnableMemoji.observeAsState().value
    val isEnableSpeechRecognition = prefs.isEnableSpeechRecognition.observeAsState().value


    CupertinoSection(
        title = { CupertinoText(stringResource(R.string.all_keyboards), style = titleTextStyle) },
        caption = {
            CupertinoText(
                text = stringResource(R.string.double_tap_space_bar),
                style = descTextStyle
            )
        }
    ) {
        SectionItem(
            title = {
                CupertinoText(
                    stringResource(R.string.auto_capitalisation), style = tileTextStyle,
                )
            },
            trailingContent = {
                CupertinoSwitch(
                    checked = isAutoCapitalization,
                    onCheckedChange = { prefs.isAutoCapitalisation.set(it) }
                )
            }
        )

//        SectionItem(
//            title = {
//                CupertinoText(
//                    text = stringResource(R.string.auto_correction),
//                    color = CupertinoColors.systemRed,
//                    style = tileTextStyle
//                )
//            },
//            trailingContent = {
//                CupertinoSwitch(
//
//                    enabled = false,
//                    checked = isAutoCorrect,
//                    onCheckedChange = { prefs.isAutoCorrect.set(it) }
//                )
//            }
//        )

//        SectionItem(
//            title = {
//                CupertinoText(
//                    text = stringResource(R.string.check_spelling),
//                    color = CupertinoColors.systemRed,
//                    style = tileTextStyle
//                )
//            },
//            trailingContent = {
//                CupertinoSwitch(
//
//                    enabled = false,
//                    checked = isCheckSpelling,
//                    onCheckedChange = { prefs.isCheckSpelling.set(it) }
//                )
//            }
//        )

        SectionItem(
            title = {
                CupertinoText(
                    stringResource(R.string.enable_caps_lock), style = tileTextStyle,
                )
            },
            trailingContent = {
                CupertinoSwitch(
                    checked = isEnableCapsLock,
                    onCheckedChange = { prefs.isEnableCapsLock.set(it) }
                )
            }
        )

        SectionItem(
            title = {
                CupertinoText(
                    stringResource(R.string.predictive), style = tileTextStyle,
                )
            },
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
                    stringResource(R.string.dot_shortcut), style = tileTextStyle,
                )
            },
            trailingContent = {
                CupertinoSwitch(

                    checked = isDotShortcut,
                    onCheckedChange = { prefs.isDotShortcut.set(it) }
                )
            }
        )

        SectionItem(
            title = {
                CupertinoText(
                    text = stringResource(R.string.character_preview),
                    style = tileTextStyle,
                    color = CupertinoColors.systemOrange,
                )
            },
            trailingContent = {
                CupertinoSwitch(
                    checked = isCharacterPreview,
                    onCheckedChange = { prefs.isCharacterPreview.set(it) }
                )
            }
        )

        SectionItem(
            title = {
                CupertinoText(
                    stringResource(R.string.enable_memoji),
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
                    stringResource(R.string.enable_accents),
                    color = CupertinoColors.systemRed,
                    style = tileTextStyle
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
            title = {
                CupertinoText(
                    stringResource(R.string.enable_speech_recognition_all),
                    style = tileTextStyle,
                    color = CupertinoColors.systemRed,
                )
            },
            trailingContent = {
                CupertinoSwitch(
                    checked = isEnableSpeechRecognition,
                    onCheckedChange = {
//                        if (!it) {
//                            prefs.isEnableSpeechRecognition.set(false).apply {
//                                settingsModel.resetApplication(context)
//                            }
//                        } else {
//                            showSpeechAlertDialog.value = true
//                        }
                    }
                )
            }
        )
    }
}