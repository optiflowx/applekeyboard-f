@file:Suppress("DEPRECATION")

package com.optiflowx.optikeysx.screens

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.optikeysx.core.preferences.PrefsConstants
import com.optiflowx.optikeysx.core.preferences.rememberPreference
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.screens.destinations.KeyboardFontsScreenDestination
import com.optiflowx.optikeysx.screens.destinations.KeyboardsScreenDestination
import com.optiflowx.optikeysx.screens.destinations.TextReplacementScreenDestination
import com.optiflowx.optikeysx.ui.cupertino.CupertinoTile
import com.optiflowx.optikeysx.ui.regular
import com.optiflowx.optikeysx.ui.sheets.CopyrightBottomSheet
import com.optiflowx.optikeysx.viewmodels.AppViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTextField
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.link
import io.github.alexzhirkevich.cupertino.section.switch
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue
import io.github.alexzhirkevich.cupertino.theme.systemGreen
import io.github.alexzhirkevich.cupertino.theme.systemOrange
import io.github.alexzhirkevich.cupertino.theme.systemYellow
import splitties.systemservices.inputMethodManager

@Suppress("UNCHECKED_CAST")
@OptIn(
    ExperimentalCupertinoApi::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
@Destination
@RootNavGraph(start = true)
fun HomeScreen(navigator: DestinationsNavigator) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val pC = PrefsConstants

    val tileTextStyle = TextStyle(
        fontSize = TextUnit(17f, TextUnitType.Sp).nonScaledSp,
        fontFamily = regular,
    )

    val titleTextStyle = TextStyle(
        fontSize = TextUnit(14f, TextUnitType.Sp).nonScaledSp,
        fontFamily = regular,
    )

    val descTextStyle = TextStyle(
        fontSize = TextUnit(13f, TextUnitType.Sp).nonScaledSp,
        fontFamily = regular,
    )

    val viewModel = viewModel<AppViewModel>(
        key = "AppViewModel",
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AppViewModel(context) as T
            }
        }
    )

    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)

    val isSound = rememberPreference(pC.SOUND_ON_KEY_PRESS_KEY, false)

    val isVibrate = rememberPreference(pC.VIBRATE_ON_KEY_PRESS_KEY, false)

    val isAutoCapitalisation = rememberPreference(pC.AUTO_CAPITALISATION_KEY, true)

    val isDotShortcut = rememberPreference(pC.DOT_SHORTCUT_KEY, true)

    val isEnableCapsLock = rememberPreference(pC.ENABLE_CAPS_LOCK_KEY, true)

    val isPredictive = rememberPreference(pC.PREDICTIVE_KEY, true)

    val isAutoCorrect = rememberPreference(pC.AUTO_CORRECTION_KEY, false)

    val isCheckSpelling = rememberPreference(pC.CHECK_SPELLING_KEY, false)

    val isCharacterPreview = rememberPreference(pC.CHARACTER_PREVIEW_KEY, false)

    val (copyrightSheet, onCopyrightSheetChanged) = remember { mutableStateOf(false) }

    val (value, onValueChange) = remember { mutableStateOf("") }

    val interactionSource = remember { MutableInteractionSource() }

    if (copyrightSheet) {
        CopyrightBottomSheet { onCopyrightSheetChanged(false) }
    }

    CupertinoScaffold(
        appBarsBlurAlpha = 0.65f,
        appBarsBlurRadius = 10.dp,
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },
        topBar = {
            CupertinoTopAppBar(
                isTranslucent = true,
                isTransparent = true,
                title = {
                    CupertinoText("OptiKeysX - Cupertino Keyboard")
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .statusBarsPadding()
                .absolutePadding(top = 40.dp)
                .testTag("home_screen_list"),
            userScrollEnabled = true
        ) {
            item("Message Section") {
                CupertinoSection {
                    item("text") {
                        CupertinoText(
                            text = "CAUTION: Acquiring this application for free from unofficial sources may expose " +
                                    "you to potential malware threats. Thus, I strongly advise against accepting or using any " +
                                    "modified versions of this app.\n" +
                                    "\n" +
                                    "Thank you for your support and understanding. If you have any concerns or questions, " +
                                    "feel free to contact me.\uD83D\uDE0A",
                            style = TextStyle(
                                fontSize = 14.sp.nonScaledSp,
                                fontFamily = regular,
                            ),
                            modifier = Modifier.padding(it)
                        )
                    }
                }
            }

            item("Services Section") {
                CupertinoSection(
                    title = { CupertinoText("SERVICES", style = titleTextStyle) },
                    caption = {
                        CupertinoText(
                            text = "You can enable or disable the keyboard from here.",
                            style = descTextStyle,
                        )
                    }
                ) {
                    this.link(
                        key = 0,
                        onClickLabel = "Enable IME Service",
                        title = { CupertinoText("Enable IME Service", style = tileTextStyle) },
                        onClick = { context.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) }
                    )
                    this.link(
                        key = 1,
                        onClickLabel = "Change IME Service",
                        title = { CupertinoText("Change IME Service", style = tileTextStyle) },
                        onClick = { inputMethodManager.showInputMethodPicker() }
                    )
                }
            }

            item("Test Keyboard") {
                CupertinoSection(
                    title = {
                        CupertinoText("TEST KEYBOARD", style = titleTextStyle)
                    },
                    caption = {
                        CupertinoText(
                            text = "This section contains test keyboards for you to try out.",
                            style = descTextStyle
                        )
                    }
                ) {
                    this.item {
                        CupertinoTextField(
                            value = value,
                            enabled = true,
                            onValueChange = onValueChange,
                            modifier = Modifier.padding(it),
                            interactionSource = interactionSource,
                            placeholder = {
                                CupertinoText("Input Test")
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                            ),
                        )
                    }
                }
            }

            item("General Section") {
                CupertinoSection(
                    title = { CupertinoText("GENERAL", style = titleTextStyle) },
                ) {
                    this.link(
                        key = 0,
                        onClickLabel = "Keyboards",
                        title = { CupertinoText("Keyboards", style = tileTextStyle) },
                        onClick = { navigator.navigate(KeyboardsScreenDestination) }
                    )
                    this.link(
                        key = 1,
                        onClickLabel = "Keyboard Fonts",
                        title = { CupertinoText("Keyboard Fonts", style = tileTextStyle) },
                        onClick = { navigator.navigate(KeyboardFontsScreenDestination) }
                    )
                    this.link(
                        key = 2,
                        onClickLabel = "Text Replacement",
                        title = {
                            CupertinoText("Text Replacement", style = tileTextStyle)
                        },
                        onClick = {
                            navigator.navigate(
                                TextReplacementScreenDestination,
                            )
                        }
                    )
                }
            }

            item("Interactions") {
                CupertinoSection(
                    title = { CupertinoText("INTERACTIONS", style = titleTextStyle) },
                ) {
                    this.switch(
                        title = {
                            CupertinoText(
                                text = "Sound On Key Press",
                                style = tileTextStyle
                            )
                        },
                        checked = isSound.value,
                        onCheckedChange = { viewModel.updateSoundOnKeyPress(it) }
                    )
                    this.switch(
                        title = {
                            CupertinoText("Vibrate On Key Press", style = tileTextStyle)
                        },
                        checked = isVibrate.value,
                        onCheckedChange = { viewModel.updateVibrateOnKeyPress(it) }
                    )
                }
            }

            item("All Keyboards Section") {
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
                            CupertinoText("Auto-Capitalisation", style = tileTextStyle)
                        },
                        checked = isAutoCapitalisation.value,
                        onCheckedChange = { viewModel.updateAutoCapitalisation(it) }
                    )
                    this.switch(
                        title = {
                            CupertinoText(
                                text = "Auto-Correction",
                                color = CupertinoColors.systemOrange,
                                style = tileTextStyle
                            )
                        },
                        checked = isAutoCorrect.value,
                        onCheckedChange = { viewModel.updateAutoCorrection(it) }
                    )
                    this.switch(
                        title = {
                            CupertinoText(
                                text = "Check Spelling",
                                color = CupertinoColors.systemOrange,
                                style = tileTextStyle
                            )
                        },
                        checked = isCheckSpelling.value,
                        onCheckedChange = { viewModel.updateCheckSpelling(it) }
                    )
                    this.switch(
                        title = {
                            CupertinoText("Enable Caps Lock", style = tileTextStyle)
                        },
                        checked = isEnableCapsLock.value,
                        onCheckedChange = { viewModel.updateEnableCapsLock(it) }
                    )
                    this.switch(
                        title = {
                            CupertinoText("Predictive", style = tileTextStyle)
                        },
                        checked = isPredictive.value,
                        onCheckedChange = { viewModel.updatePredictive(it) }
                    )

                    this.switch(
                        title = {
                            CupertinoText(
                                text = "Character Preview",
                                color = CupertinoColors.systemYellow,
                                style = tileTextStyle
                            )
                        },
                        checked = isCharacterPreview.value,
                        onCheckedChange = { viewModel.updateCharacterPreview(it) }
                    )
                    this.switch(
                        title = {
                            CupertinoText("\".\" Shortcut", style = tileTextStyle)
                        },
                        checked = isDotShortcut.value,
                        onCheckedChange = { viewModel.updateDotShortcut(it) }
                    )
                }
            }

            item("App Information Section") {
                CupertinoSection(
                    title = { CupertinoText("APP INFORMATION", style = titleTextStyle) }
                ) {
                    this.item(0) {
                        CupertinoTile(
                            title = "Version",
                            trailingText = packageInfo.versionName,
                            trailingIcon = null,
                        )
                    }
                    this.item(1) {
                        CupertinoTile(
                            title = "Package Name",
                            trailingText = packageInfo.packageName,
                            trailingIcon = null,
                        )
                    }
                    this.item(2) {
                        CupertinoTile(
                            title = "Build Version",
                            trailingText = packageInfo.versionCode.toString(),
                            trailingIcon = null,
                        )
                    }
                }
            }

            item("Developer Section") {
                CupertinoSection(
                    title = { CupertinoText("DEVELOPER", style = titleTextStyle) },
                    caption = {
                        CupertinoText(
                            text = "This section contains important information from the developer.",
                            style = descTextStyle
                        )
                    }
                ) {
                    this.link(
                        onClickLabel = "Copyright Information",
                        title = {
                            CupertinoText(
                                text = "Copyright Information",
                                color = CupertinoColors.systemBlue,
                                style = tileTextStyle
                            )
                        },
                        onClick = { onCopyrightSheetChanged(true) }
                    )
                    this.link(
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
        }
    }
}

