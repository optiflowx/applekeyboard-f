@file:Suppress("DEPRECATION")

package com.optiflowx.applekeyboard.screens

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.core.preferences.PreferencesConstants
import com.optiflowx.applekeyboard.screens.destinations.KeyboardFontsScreenDestination
import com.optiflowx.applekeyboard.screens.destinations.KeyboardsScreenDestination
import com.optiflowx.applekeyboard.screens.destinations.TextReplacementScreenDestination
import com.optiflowx.applekeyboard.ui.cupertino.CupertinoTile
import com.optiflowx.applekeyboard.ui.home.CopyrightView
import com.optiflowx.applekeyboard.ui.regular
import com.optiflowx.applekeyboard.utils.nonScaledSp
import com.optiflowx.applekeyboard.viewmodels.AppViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.AppleLogo
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.link
import io.github.alexzhirkevich.cupertino.section.switch
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue
import io.github.alexzhirkevich.cupertino.theme.systemGreen
import io.github.alexzhirkevich.cupertino.theme.systemRed
import io.github.alexzhirkevich.cupertino.theme.systemYellow
import splitties.systemservices.inputMethodManager

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalCupertinoApi::class)
@Destination(start = true)
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val pC = PreferencesConstants

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

    val isSound = viewModel.preferences.getFlowPreference(pC.SOUND_ON_KEY_PRESS_KEY, false)
        .collectAsStateWithLifecycle(false)
    val isVibrate = viewModel.preferences.getFlowPreference(pC.VIBRATE_ON_KEY_PRESS_KEY, false)
        .collectAsStateWithLifecycle(false)
    val isAutoCapitalisation = viewModel.preferences.getFlowPreference(pC.AUTO_CAPITALISATION_KEY, true)
            .collectAsStateWithLifecycle(true)
    val isDotShortcut = viewModel.preferences.getFlowPreference(pC.DOT_SHORTCUT_KEY, true)
            .collectAsStateWithLifecycle(true)
    val isEnableCapsLock = viewModel.preferences.getFlowPreference(pC.ENABLE_CAPS_LOCK_KEY, true)
        .collectAsStateWithLifecycle(true)
    val isPredictive = viewModel.preferences.getFlowPreference(pC.PREDICTIVE_KEY, true)
        .collectAsStateWithLifecycle(true)
    val isAutoCorrect = viewModel.preferences.getFlowPreference(pC.AUTO_CORRECTION_KEY, false)
        .collectAsStateWithLifecycle(false)
    val isCheckSpelling = viewModel.preferences.getFlowPreference(pC.CHECK_SPELLING_KEY, false)
        .collectAsStateWithLifecycle(false)
    val isCharacterPreview = viewModel.preferences.getFlowPreference(pC.CHARACTER_PREVIEW_KEY, false)
            .collectAsStateWithLifecycle(false)

    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) { BottomSheet { showSheet = false } }

    CupertinoScaffold(
        appBarsBlurAlpha = 0.65f,
        appBarsBlurRadius = 10.dp,
        topBar = {
            CupertinoTopAppBar(
                isTranslucent = true,
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CupertinoIcon(
                            CupertinoIcons.Default.AppleLogo,
                            "logo",
                        )
                        CupertinoText("  Keyboard")
                    }
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
            item("Message Section") {
                CupertinoSection {
                    item("text") {
                        CupertinoText(
                            text = "This application is developed solely by OptiFlowX. Your purchase is a valuable " +
                                    "expression of your support for the dedication and effort put into creating and maintaining this app.\n" +
                                    "\n" +
                                    "CAUTION: Acquiring this application for free from unofficial sources may expose " +
                                    "you to potential malware threats. Thus, I strongly advise against accepting or using any " +
                                    "modified versions of this app.\n" +
                                    "\n" +
                                    "Thank you for your support and understanding. If you have any concerns or questions, " +
                                    "feel free to contact me.\uD83D\uDE0A",
                            style = TextStyle(
                                fontSize = TextUnit(14f, TextUnitType.Sp).nonScaledSp,
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
                        title = { CupertinoText("Enable IME Service", style = tileTextStyle) },
                        onClick = { context.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) }
                    )
                    this.link(
                        key = 1,
                        title = { CupertinoText("Change IME Service", style = tileTextStyle) },
                        onClick = { inputMethodManager.showInputMethodPicker() }
                    )
                }
            }

            item("General Section") {
                CupertinoSection(
                    title = { CupertinoText("GENERAL", style = titleTextStyle) },
                ) {
                    this.link(
                        key = 0,
                        title = { CupertinoText("Keyboards", style = tileTextStyle) },
                        onClick = { navigator.navigate(KeyboardsScreenDestination) }
                    )
                    this.link(
                        key = 1,
                        title = { CupertinoText("Keyboard Fonts", style = tileTextStyle) },
                        onClick = { navigator.navigate(KeyboardFontsScreenDestination) }
                    )
                    this.link(
                        key = 2,
                        title = {
                            CupertinoText("Text Replacement", style = tileTextStyle)
                        },
                        onClick = { navigator.navigate(TextReplacementScreenDestination) }
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
                                color = CupertinoColors.systemYellow,
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
                                color = CupertinoColors.systemYellow,
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
                                color = CupertinoColors.systemRed,
                                style = tileTextStyle
                            )
                        },
                        checked = isCharacterPreview.value,
                        onCheckedChange = {}
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

            item("Interactions") {
                CupertinoSection(
                    title = { CupertinoText("INTERACTIONS", style = titleTextStyle) },
                ) {
                    this.switch(
                        title = {
                            CupertinoText(
                                text = "Sound On Key Press",
                                style = tileTextStyle)
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
                        title = {
                            CupertinoText(
                                text = "Copyright Information",
                                color = CupertinoColors.systemBlue,
                                style = tileTextStyle
                            )
                        },
                        onClick = { showSheet = true }
                    )
                    this.link(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        containerColor = Color.Black,
        windowInsets = WindowInsets.safeDrawing,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column {
            Text(
                "Copyright Notice",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                fontFamily = regular,
                fontSize = TextUnit(19f, TextUnitType.Sp).nonScaledSp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(6.dp))
            CopyrightView()
            Spacer(Modifier.height(20.dp))
        }
    }
}
