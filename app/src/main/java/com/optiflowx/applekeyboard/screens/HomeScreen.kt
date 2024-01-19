package com.optiflowx.applekeyboard.screens

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.composables.home.CopyrightView
import com.optiflowx.applekeyboard.composables.home.ImportantNotice
import com.optiflowx.applekeyboard.screens.destinations.KeyboardSettingsScreenDestination
import com.optiflowx.applekeyboard.screens.destinations.KeyboardTestScreenDestination
import com.optiflowx.applekeyboard.ui.cupertinoBlue1
import com.optiflowx.applekeyboard.ui.regular
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoButtonDefaults
import io.github.alexzhirkevich.cupertino.CupertinoButtonSize
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.Surface
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.AppleLogo
import splitties.systemservices.inputMethodManager

@OptIn(ExperimentalCupertinoApi::class)
@Destination(start = true)
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    CupertinoScaffold(
        containerColor = Color.Black,
        contentColor = Color.White,
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = { HomeTopBar() },
        bottomBar = { HomeBottomBar() }
    ) {
        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp)
        ) {
            LazyColumn {
                this.item {
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ImportantNotice()
                        CupertinoButton(
                            size = CupertinoButtonSize.Small,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                context.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
                            },
                        ) { Text(text = "Enable IME Service") }
                        Spacer(modifier = Modifier.height(8.dp))
                        CupertinoButton(
                            size = CupertinoButtonSize.Small,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { inputMethodManager.showInputMethodPicker() },
                        ) { Text(text = "Change IME Service") }
                        Spacer(modifier = Modifier.height(8.dp))
                        CupertinoButton(
                            size = CupertinoButtonSize.Small,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                navigator.navigate(KeyboardTestScreenDestination)
                            },
                        ) { Text(text = "Test Keyboard") }
                        Spacer(modifier = Modifier.height(8.dp))
                        CupertinoButton(
                            size = CupertinoButtonSize.Small,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                navigator.navigate(KeyboardSettingsScreenDestination)
                            },
                        ) { Text(text = "Keyboard Settings") }
                        Spacer(modifier = Modifier.height(8.dp))
                        CupertinoButton(
                            size = CupertinoButtonSize.Small,
                            modifier = Modifier.fillMaxWidth(),
                            colors = CupertinoButtonDefaults.borderlessButtonColors(
                                contentColor = Color.White,
                                containerColor = Color.Red.copy(alpha = 0.7f),
                            ),
                            onClick = {
                                uriHandler.openUri("https://t.me/optiflowxparadise/")
                            },
                        ) { Text(text = "Join For Updates") }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun HomeTopBar() {
    CupertinoTopAppBar(
        isTransparent = true,
        title = {
            Row {
                Icon(
                    CupertinoIcons.Default.AppleLogo,
                    "logo",
                    tint = Color.White,
                )
                Text(" Keyboard", color = Color.White)
            }
        }
    )
}

@Composable
fun HomeBottomBar() {
    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        BottomSheet() { showSheet = false }
    }

    Text(
        "Copyright Information",
        color = cupertinoBlue1,
        textAlign = TextAlign.Center,
        fontFamily = regular,
        fontWeight = FontWeight.ExtraBold,
        fontSize = TextUnit(14f, TextUnitType.Sp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { showSheet = true })
            .padding(10.dp)
    )
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
                color = Color.White,
                fontSize = TextUnit(22f, TextUnitType.Sp),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(6.dp))
            CopyrightView()
            Spacer(Modifier.height(20.dp))
        }
    }
}
