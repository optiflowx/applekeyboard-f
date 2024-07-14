package com.optiflowx.optikeysx.app.screens.speech

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.app.cupertino.CupertinoTile
import com.optiflowx.optikeysx.core.Tools
import com.optiflowx.optikeysx.core.enums.KeepScreenAwakeMode
import com.optiflowx.optikeysx.optikeysxPreferences
import com.optiflowx.optikeysx.ime.theme.bold
import com.optiflowx.optikeysx.app.viewmodels.KeyboardSettingsModel
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.CupertinoDropdownMenu
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoNavigateBackButton
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoSwitch
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.MenuAction
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Plus
import io.github.alexzhirkevich.cupertino.icons.outlined.Trash
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.CupertinoSectionDefaults
import io.github.alexzhirkevich.cupertino.section.LocalSectionStyle
import io.github.alexzhirkevich.cupertino.section.SectionItem
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import io.github.alexzhirkevich.cupertino.theme.systemBlue
import io.github.alexzhirkevich.cupertino.theme.systemGray3
import io.github.alexzhirkevich.cupertino.theme.systemRed
import kotlinx.coroutines.delay


class VoiceRecognitionSettingsScreen : Screen {
    val prefs by optikeysxPreferences()

    override val key: ScreenKey = uniqueScreenKey

    companion object {
        const val TAG = "VoiceRecognitionSettingsScreen"
    }

    @OptIn(ExperimentalCupertinoApi::class, ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current

        val navigator = LocalNavigator.currentOrThrow

        val settingsModel = rememberScreenModel { KeyboardSettingsModel() }

        val modelsList by prefs.modelsOrder.observeAsState()

        val keepScreenAwake = prefs.keepScreenAwake.observeAsState().value

        val keepModelInMemory = prefs.keepLanguageModelInMemory.observeAsState().value

        val (dropdownVisible, setDropdownVisible) = remember { mutableStateOf(false) }


        LaunchedEffect(Unit) {
            settingsModel.initRecognizerSourceProviders(context).run {
                delay(250L)
                settingsModel.reloadModels()
            }
        }

        CupertinoScaffold(
            modifier = Modifier.semantics { testTagsAsResourceId = true },
            containerColor = CupertinoSectionDefaults.containerColor(LocalSectionStyle.current),
            topBar = {
                CupertinoTopAppBar(
                    modifier = Modifier.padding(end = 15.dp),
                    navigationIcon = {
                        CupertinoNavigateBackButton(onClick = { navigator.popUntilRoot() }) {
                            CupertinoText(stringResource(R.string.back))
                        }
                    },
                    title = {
                        CupertinoText(
                            text = stringResource(R.string.speech_keyboard),
                            fontFamily = bold,
                        )
                    },
                    actions = {
                        CupertinoIcon(
                            imageVector = CupertinoIcons.Default.Plus,
                            contentDescription = "add",
                            modifier = Modifier.clickable(
                                onClick = {
                                    navigator.push(ModelDownloadScreen(modelsList))
                                }
                            )
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
                CupertinoSection(
                    title = { CupertinoText(stringResource(R.string.settings)) }
                ) {
                    SectionItem(
                        title = { CupertinoText(text = stringResource(R.string.keep_screen_awake)) },
                        trailingContent = {
                            Box {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = CupertinoColors.systemGray3.copy(alpha = 0.2f),
                                            shape = CupertinoTheme.shapes.small
                                        )
                                        .clickable {
                                            setDropdownVisible(true)
                                        }
                                ) {
                                    CupertinoText(
                                        text = keepScreenAwake.name,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            color = CupertinoColors.systemBlue,
                                            fontFamily = bold,
                                        ),
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }


                                CupertinoDropdownMenu(
                                    expanded = dropdownVisible,
                                    onDismissRequest = { setDropdownVisible(false) }
                                ) {
                                    KeepScreenAwakeMode.listEntries().forEach { mode ->
                                        MenuAction(
                                            title = { CupertinoText(mode.key.name) },
                                            onClick = {
                                                prefs.keepScreenAwake.set(mode.key)
                                                setDropdownVisible(false)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    )

                    SectionItem(
                        title = { CupertinoText(stringResource(R.string.keep_model_in_memory)) },
                        trailingContent = {
                            CupertinoSwitch(
                                checked = keepModelInMemory,
                                onCheckedChange = { prefs.keepLanguageModelInMemory.set(it) }
                            )
                        }
                    )
                }

                CupertinoSection(title = { CupertinoText(stringResource(R.string.downloaded_models)) }) {
                    modelsList.forEach { model ->
                        CupertinoTile(
                            title = model.name,
                            trailingIcon = CupertinoIcons.Default.Trash,
                            trailingTint = CupertinoColors.systemRed,
                            trailingIconSize = 25,
                            onClick = {
                                Tools.deleteModel(model).apply {
                                    settingsModel.reloadModels()
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}



