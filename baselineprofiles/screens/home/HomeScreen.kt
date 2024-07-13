package com.optiflowx.optikeysx.screens.home

import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.screens.components.TopBar
import com.optiflowx.optikeysx.ui.bold
import com.optiflowx.optikeysx.ui.cupertino.CopyrightBottomSheet
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
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme


class HomeScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    companion object {
        const val TAG = "HomeScreen"
    }

    @Composable
    @OptIn(ExperimentalCupertinoApi::class, ExperimentalComposeUiApi::class)
    override fun Content() {
        val sheetSectionColor = CupertinoTheme.colorScheme.tertiarySystemBackground

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

        val lazyListState = rememberLazyListState()

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

        LaunchedEffect(lazyListState.isScrollInProgress) {
            if (lazyListState.isScrollInProgress) {
                focusManager.clearFocus(force = true)
            }
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
                sheetContainerColor = CupertinoTheme.colorScheme
                    .secondarySystemBackground,
            ),
            sheetContent = {
                CopyrightBottomSheet(
                    scaffoldState = scaffoldState,
                    sheetSectionColor = sheetSectionColor
                )
            },
            scaffoldState = scaffoldState,
            topBar = {
                TopBar(
                    lazyListState = lazyListState,
                    title = "OptiKeysX"
                )
            },
        ) {
            LazyColumn(
                modifier = Modifier
                    .statusBarsPadding()
                    .absolutePadding(top = 40.dp)
                    .testTag("home_screen_list"),
                userScrollEnabled = true
            ) {
                item {
                    CupertinoNavigationTitle {
                        CupertinoText("OptiKeysX", style = navigationTextStyle)
                    }
                }

                item("Message Section") {
                    MessageSection()
                }

                item("Test Keyboard") {
                    TestKeyboardSection(
                        titleTextStyle = titleTextStyle,
                        descTextStyle = descTextStyle,
                    )
                }

                item("General Section") {
                    GeneralSection(
                        titleTextStyle = titleTextStyle,
                        tileTextStyle = tileTextStyle,

                        )
                }

                item("Interactions") {
                    InteractionsSection(
                        titleTextStyle = titleTextStyle,
                        tileTextStyle = tileTextStyle,
                    )
                }

                item("All Keyboards Section") {
                    AllKeyboardsSection(
                        titleTextStyle = titleTextStyle,
                        tileTextStyle = tileTextStyle,
                        descTextStyle = descTextStyle,
                    )
                }

                item("App Information Section") {
                    AppInformationSection(
                        titleTextStyle = titleTextStyle
                    )
                }

                item("Developer Section") {
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
}

