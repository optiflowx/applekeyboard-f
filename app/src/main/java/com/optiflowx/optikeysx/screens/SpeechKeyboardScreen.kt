package com.optiflowx.optikeysx.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.optiflowx.optikeysx.core.Tools
import com.optiflowx.optikeysx.core.enums.KeepScreenAwakeMode
import com.optiflowx.optikeysx.optikeysxPreferences
import com.optiflowx.optikeysx.ui.bold
import com.optiflowx.optikeysx.ui.cupertino.DownloadModelBottomSheet
import com.optiflowx.optikeysx.viewmodels.KeyboardSettingsModel
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.CupertinoDropdownMenu
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoNavigateBackButton
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.MenuAction
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Plus
import io.github.alexzhirkevich.cupertino.icons.outlined.Trash
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.switch
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

        val modelOrder by prefs.modelsOrder.observeAsState()

        val (sheetState, onSheetChange) = remember { mutableStateOf(false) }

        val sheetSectionColor = CupertinoTheme.colorScheme.tertiarySystemBackground

        val keepScreenAwake = prefs.keepScreenAwake.observeAsState().value
        val keepModelInMemory = prefs.keepLanguageModelInMemory.observeAsState().value
//        val recognizeImmediately = prefs.startRecognitionInstantaneously.observeAsState().value
        val (dropdownVisible, setDropdownVisible) = remember { mutableStateOf(false) }

        if (sheetState) {
            DownloadModelBottomSheet(
                onSheetChange = onSheetChange,
                sheetSectionColor = sheetSectionColor,
                modelOrder = modelOrder,
            )
        }

        LaunchedEffect(Unit) {

           settingsModel.initRecognizerSourceProviders(context).run {
               delay(250L)
               settingsModel.reloadModels()
           }
        }

        CupertinoScaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            topBar = {
                CupertinoTopAppBar(
                    modifier = Modifier.padding(end = 15.dp),
                    navigationIcon = {
                        CupertinoNavigateBackButton(onClick = { navigator.popUntilRoot() }) {
                            CupertinoText("Home")
                        }
                    },
                    title = {
                        CupertinoText(
                            text = "Speech Recognition",
                            fontFamily = bold,
                        )
                    },
                    actions = {
                        CupertinoIcon(
                            imageVector = CupertinoIcons.Default.Plus,
                            contentDescription = "add",
                            modifier = Modifier.clickable(
                                onClick = { onSheetChange(true) }
                            )
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

                item("SETTINGS") {
                    CupertinoSection(
                        title = { CupertinoText("SETTINGS") }
                    ) {
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(it)
                            ) {
                                CupertinoText(text = "Keep Screen Awake")

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
                        }

                        switch(
                            title = { CupertinoText("Keep Model In Memory") },
                            checked = keepModelInMemory,
                            onCheckedChange = { prefs.keepLanguageModelInMemory.set(it) }
                        )
//                        switch(
//                            title = { CupertinoText("Recognize Immediately") },
//                            checked = recognizeImmediately,
//                            onCheckedChange = { prefs.startRecognitionInstantaneously.set(it) }
//                        )
                    }
                }


                item("Installed Models", modelOrder) {
                    CupertinoSection(title = { CupertinoText("DOWNLOADED MODELS") }) {
                        modelOrder.forEach { model ->
                            item(model.path) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    CupertinoText(
                                        text = model.name,
                                        modifier = Modifier.padding(10.dp),
                                    )

                                    IconButton(onClick = {
                                        Tools.deleteModel(model, context)
                                        settingsModel.reloadModels()
                                    }) {
                                        Icon(
                                            imageVector = CupertinoIcons.Default.Trash,
                                            modifier = Modifier.height(25.dp),
                                            contentDescription = "delete",
                                            tint = CupertinoColors.systemRed
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



