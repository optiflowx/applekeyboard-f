package com.optiflowx.optikeysx.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.optikeysxPreferences
import com.optiflowx.optikeysx.screens.components.TopBar
import com.optiflowx.optikeysx.screens.home.components.AllKeyboardsSection
import com.optiflowx.optikeysx.screens.home.components.AppInformationSection
import com.optiflowx.optikeysx.screens.home.components.DeveloperSection
import com.optiflowx.optikeysx.screens.home.components.GeneralSection
import com.optiflowx.optikeysx.screens.home.components.InteractionsSection
import com.optiflowx.optikeysx.screens.home.components.MessageSection
import com.optiflowx.optikeysx.screens.home.components.TestKeyboardSection
import com.optiflowx.optikeysx.ui.bold
import com.optiflowx.optikeysx.ui.cupertino.CopyrightBottomSheet
import com.optiflowx.optikeysx.ui.cupertino.JetPrefCupertinoAlertDialog
import com.optiflowx.optikeysx.ui.regular
import com.optiflowx.optikeysx.viewmodels.KeyboardSettingsModel
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffold
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldDefaults
import io.github.alexzhirkevich.cupertino.CupertinoNavigationTitle
import io.github.alexzhirkevich.cupertino.CupertinoSheetValue
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.PresentationDetent
import io.github.alexzhirkevich.cupertino.PresentationStyle
import io.github.alexzhirkevich.cupertino.rememberCupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.rememberCupertinoSheetState
import io.github.alexzhirkevich.cupertino.section.CupertinoSectionDefaults
import io.github.alexzhirkevich.cupertino.section.LocalSectionStyle
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme


class HomeScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    private val prefs by optikeysxPreferences()

    companion object {
        const val TAG = "HomeScreen"
    }

    @Composable
    @OptIn(ExperimentalCupertinoApi::class, ExperimentalComposeUiApi::class)
    override fun Content() {
        val settingsModel = rememberScreenModel { KeyboardSettingsModel() }
        val context = LocalContext.current
        val focusManager = LocalFocusManager.current

        val tileTextStyle = TextStyle(
            fontSize = 17.sp.nonScaledSp,
            fontFamily = regular,
        )

        val titleTextStyle = TextStyle(
            fontSize = 14.sp.nonScaledSp,
            fontFamily = regular,
        )

        val navigationTextStyle = TextStyle(
            fontSize = 32.sp.nonScaledSp,
            fontFamily = bold,
        )

        val descTextStyle = TextStyle(
            fontSize = 13.sp.nonScaledSp,
            fontFamily = regular,
        )

        val scrollState = rememberScrollState()

        val showSpeechAlertDialog = remember { mutableStateOf(false) }

        val scaffoldState = rememberCupertinoBottomSheetScaffoldState(
            rememberCupertinoSheetState(
                presentationStyle = PresentationStyle.Modal(
                    detents = setOf(
                        PresentationDetent.Large,
                        PresentationDetent.Fraction(.6f),
                    ),
                )
            )
        )

        if (showSpeechAlertDialog.value) {
            JetPrefCupertinoAlertDialog(
                title = stringResource(R.string.do_you_want_to_continue),
                onDismiss = { showSpeechAlertDialog.value = false },
                onOutsideDismissal = { showSpeechAlertDialog.value = false },
                onConfirm = {
                    showSpeechAlertDialog.value = false
                    prefs.isEnableSpeechRecognition.set(true).apply {
                        settingsModel.resetApplication(context)
                    }
                },
            ) {
                CupertinoText(
                    stringResource(R.string.experimental_feature_warning),
                    textAlign = TextAlign.Center,
                    style = descTextStyle
                )
            }
        }

        LaunchedEffect(scrollState.isScrollInProgress) {
            focusManager.clearFocus(force = true)
        }

        LaunchedEffect(scaffoldState.bottomSheetState.targetValue) {
            if (scaffoldState.bottomSheetState.targetValue == CupertinoSheetValue.Hidden) {
                focusManager.clearFocus(force = true)
            }
        }

        CupertinoBottomSheetScaffold(
            hasNavigationTitle = true,
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            colors = CupertinoBottomSheetScaffoldDefaults.colors(
                containerColor = CupertinoSectionDefaults.containerColor(LocalSectionStyle.current),
                sheetContainerColor = CupertinoTheme.colorScheme
                    .secondarySystemBackground,
            ),
            sheetContent = {
                CopyrightBottomSheet(
                    scaffoldState = scaffoldState
                )
            },
            scaffoldState = scaffoldState,
            topBar = {
                TopBar(
                    lazyListState = rememberLazyListState(),
                    title = "OptiKeysX"
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .absolutePadding(top = 40.dp)
                    .verticalScroll(scrollState)
                    .testTag("home_screen_list"),
            ) {
                CupertinoNavigationTitle {
                    CupertinoText("OptiKeysX", style = navigationTextStyle)
                }

                MessageSection()

                TestKeyboardSection(
                    titleTextStyle = titleTextStyle,
                    descTextStyle = descTextStyle,
                )

                GeneralSection(
                    titleTextStyle = titleTextStyle,
                    tileTextStyle = tileTextStyle,
                )

                InteractionsSection(
                    titleTextStyle = titleTextStyle,
                    tileTextStyle = tileTextStyle,
                )

                AllKeyboardsSection(
                    titleTextStyle = titleTextStyle,
                    tileTextStyle = tileTextStyle,
                    descTextStyle = descTextStyle,
                    showSpeechAlertDialog = showSpeechAlertDialog,
                    settingsModel = settingsModel
                )

                AppInformationSection(
                    titleTextStyle = titleTextStyle
                )

                DeveloperSection(
                    titleTextStyle = titleTextStyle,
                    tileTextStyle = tileTextStyle,
                    descTextStyle = descTextStyle,
                    scaffoldState = scaffoldState,
                )
            }
        }
    }
}

