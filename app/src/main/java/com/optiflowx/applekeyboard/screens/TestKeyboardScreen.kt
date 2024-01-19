package com.optiflowx.applekeyboard.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.composables.home.TextFieldView
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.Surface
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.ChevronBackward

@OptIn(ExperimentalCupertinoApi::class)
@Destination
@Composable
fun KeyboardTestScreen(navigator: DestinationsNavigator) {
    val (text, setValue) = remember { mutableStateOf(TextFieldValue("")) }
    val (text2, setValue2) = remember { mutableStateOf(TextFieldValue("")) }
    val (text3, setValue3) = remember { mutableStateOf(TextFieldValue("")) }
    val (text4, setValue4) = remember { mutableStateOf(TextFieldValue("")) }
    val (text5, setValue5) = remember { mutableStateOf(TextFieldValue("")) }
    val (text6, setValue6) = remember { mutableStateOf(TextFieldValue("")) }
    val (text7, setValue7) = remember { mutableStateOf(TextFieldValue("")) }

    CupertinoScaffold(
        topBar = { TestScreenTopBar(navigator) },
        containerColor = Color.Black,
        contentColor = Color.White,
        contentWindowInsets = WindowInsets.safeContent,
    ) {
        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Column {
                Spacer(Modifier.height(40.dp))
                TextFieldView("General", text4, setValue4)
                TextFieldView("Text", text, setValue)
                TextFieldView("Number", text2, setValue2)
                TextFieldView("Phone", text3, setValue3)
                TextFieldView("URL", text5, setValue5)
                TextFieldView("Search", text6, setValue6)
                TextFieldView("Next", text7, setValue7)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalCupertinoApi::class)
fun TestScreenTopBar(navigator: DestinationsNavigator) {
    CupertinoTopAppBar(
        isTransparent = true,
        title = {Text("Test Keyboard", color = Color.White)},
        modifier = Modifier.padding(vertical = 5.dp),
        navigationIcon = {
            IconButton(onClick = {navigator.popBackStack()}) {
                CupertinoIcon(
                    CupertinoIcons.Default.ChevronBackward,
                    "backButton"
                )
            }
        }
    )
}