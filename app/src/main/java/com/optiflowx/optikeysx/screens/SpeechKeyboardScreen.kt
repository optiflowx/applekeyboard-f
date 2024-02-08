package com.optiflowx.optikeysx.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.optiflowx.optikeysx.core.Tools
import com.optiflowx.optikeysx.core.downloader.messages.CancelCurrent
import com.optiflowx.optikeysx.core.downloader.messages.CancelPending
import com.optiflowx.optikeysx.core.downloader.messages.State
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.optikeysxPreferences
import com.optiflowx.optikeysx.ui.bold
import com.optiflowx.optikeysx.ui.cupertino.DownloadModelBottomSheet
import com.optiflowx.optikeysx.ui.regular
import com.optiflowx.optikeysx.viewmodels.KeyboardSettingsModel
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffold
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldDefaults
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoNavigateBackButton
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.PresentationDetent
import io.github.alexzhirkevich.cupertino.PresentationStyle
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Plus
import io.github.alexzhirkevich.cupertino.icons.outlined.Xmark
import io.github.alexzhirkevich.cupertino.rememberCupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.rememberCupertinoSheetState
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import org.greenrobot.eventbus.EventBus


class SpeechKeyboardScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    companion object {
        const val TAG = "SpeechKeyboardScreen"
    }

    @OptIn(ExperimentalCupertinoApi::class , ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val prefs by optikeysxPreferences()

        val context = LocalContext.current
        val settingsModel = rememberScreenModel(TAG) {
            KeyboardSettingsModel()
        }


        val modelsPendingDownloadState = settingsModel.modelsPendingDownloadLD.collectAsState().value
        val currentDownloadingModelState = settingsModel.currentDownloadingModel.collectAsState().value

        val tileTextStyle = TextStyle(
            fontSize = TextUnit(17f, TextUnitType.Sp).nonScaledSp,
            fontFamily = regular,
        )

        val navigator = LocalNavigator.currentOrThrow

        val modelOrder by prefs.modelsOrder.observeAsState()

        val scope = rememberCoroutineScope()

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

        var modelOrderData by remember {
            mutableStateOf(modelOrder)
        }

        modelOrderData = modelOrder

        val sheetSectionColor = CupertinoTheme.colorScheme.tertiarySystemBackground

        val state = rememberReorderableLazyListState(onMove = { from, to ->
            // one for currently downloading, one for the separator, and all the pending downloads
            val fromIndex = from.index - modelsPendingDownloadState.size - 2
            val toIndex = to.index - modelsPendingDownloadState.size - 2
            if (fromIndex < 0 || toIndex < 0) return@rememberReorderableLazyListState

            prefs.modelsOrder.set(modelOrderData.toMutableList().apply {
                add(
                    toIndex, removeAt(fromIndex)
                )
            })
        })

        LaunchedEffect(Unit) {
            settingsModel.reloadModels()
        }

        CupertinoBottomSheetScaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            colors = CupertinoBottomSheetScaffoldDefaults.colors(
                sheetContainerColor = CupertinoTheme.colorScheme
                    .secondarySystemBackground,
            ),
            sheetContent = {
                DownloadModelBottomSheet(
                    scaffoldState = scaffoldState,
                    sheetSectionColor = sheetSectionColor,
                    modelOrder = modelOrder,
                )
            },
            scaffoldState = scaffoldState,
            topBar = {
                CupertinoTopAppBar(
                    modifier = Modifier.padding(end = 15.dp),
                    navigationIcon = {
                        CupertinoNavigateBackButton(onClick = { navigator.pop() }) {
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
                                onClick = {
                                    scope.launch {
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                }
                            )
                        )
                    }
                )
            }
        ) {
            LazyColumn(
                state = state.listState,
                modifier = Modifier
                    .statusBarsPadding()
                    .absolutePadding(top = 40.dp)
                    .reorderable(state)
                    .detectReorderAfterLongPress(state),
                userScrollEnabled = true
            ) {
                item {
                    currentDownloadingModelState.let { current ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Card {
                                Column {
                                    Row(
                                        modifier = Modifier.padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (current != null) {
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = current.info.locale.displayName,
                                                    fontSize = 20.sp
                                                )
                                                Text(text = current.info.url, fontSize = 12.sp)
                                                val stateText = when (current.state) {
                                                    State.NONE -> "Download Unknown"
                                                    State.QUEUED -> "Download Pending"
                                                    State.DOWNLOAD_STARTED -> "Download Started"
                                                    State.DOWNLOAD_FINISHED -> "Download Finished"
                                                    State.UNZIP_STARTED -> "Unzip Started"
                                                    State.UNZIP_FINISHED -> "Unzip Finished"
                                                    State.FINISHED -> "Completed"
                                                    State.ERROR -> "An error occurred while downloading"
                                                    State.CANCELED -> "Download Canceled"
                                                }

                                                Text(
                                                    text = "OptiKeysX: Downloading Model".format(
                                                        stateText
                                                    ), fontSize = 14.sp
                                                )
                                            }
                                        }
                                        IconButton(onClick = {
                                            if (current != null) {
                                                EventBus.getDefault().post(
                                                    CancelCurrent(current.info)
                                                )
                                            }
                                        }) {
                                            Icon(
                                                imageVector = CupertinoIcons.Default.Xmark,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                    current?.progress?.let { it1 ->
                                        LinearProgressIndicator(
                                            it1, modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    modelsPendingDownloadState.forEach {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {

                            Card {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = it.locale.displayName, fontSize = 20.sp)
                                        Text(text = it.url, fontSize = 12.sp)
                                        Text(
                                            text = "Download Pending...",
                                            fontSize = 14.sp
                                        )
                                    }
                                    IconButton(onClick = {
                                        EventBus.getDefault().post(
                                            CancelPending(it)
                                        )
                                    }) {
                                        Icon(
                                            imageVector = CupertinoIcons.Default.Xmark,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    if (currentDownloadingModelState != null || modelsPendingDownloadState.isNotEmpty()) {
                        Spacer(
                            modifier = Modifier
                                .padding(10.dp, 5.dp)
                                .background(MaterialTheme.colorScheme.primary)
                                .fillMaxWidth()
                                .height(2.dp)
                        )
                    }
                }

                item {
                    modelOrderData.forEach {
                        ReorderableItem(
                            state, key = it.path, modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) { isDragging ->
                            val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                            Card(
                                modifier = Modifier
                                    .shadow(elevation.value)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                        .padding(10.dp)
                                ) {

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = it.name,
                                            fontSize = 20.sp
                                        )
                                        Text(text = it.path, fontSize = 12.sp)
                                    }
                                    IconButton(onClick = {
                                        Tools.deleteModel(it, context)
                                        settingsModel.reloadModels()
                                    }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
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



