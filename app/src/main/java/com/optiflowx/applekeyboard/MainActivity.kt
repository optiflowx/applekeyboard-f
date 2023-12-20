@file:Suppress("UNCHECKED_CAST")

package com.optiflowx.applekeyboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.ui.AppleKeyboardIMETheme
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.AppleLogo
import splitties.systemservices.inputMethodManager

class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalCupertinoApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppleKeyboardIMETheme {
                val colors = MaterialTheme.colors
                val screenWidth = LocalConfiguration.current.screenWidthDp

                val viewModel = viewModel<KeyboardViewModel>(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return KeyboardViewModel(screenWidth, colors) as T
                        }
                    }
                )

                LaunchedEffect(Unit) {
//                    if (!appSettings.isFirstRun) {
//                        startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
//                    }
                }

                CupertinoScaffold(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    topBar = {
                        CupertinoTopAppBar(
                            windowInsets = WindowInsets.safeContent,
                            isTransparent = true,
                            title = {
                                Row {
                                    Icon(
                                        CupertinoIcons.Default.AppleLogo,
                                        "logo",
//                                        tint = Color.White
                                    )
                                    Text(" Apple Keyboard")
                                }
                            }
                        )
                    },
                ) {
                    //Disclaimer Block
                    //Caution Block
                    Options()
                }
                isSystemInDarkTheme()
            }
        }
    }
}

@Composable
fun Options() {
    Column(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeContent)) {
        val ctx = LocalContext.current
        val (text, setValue) = remember { mutableStateOf(TextFieldValue("Try here")) }
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            ctx.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }) {
            Text(text = "Enable IME")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            inputMethodManager.showInputMethodPicker()
        }) {
            Text(text = "Select IME")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = text, onValueChange = setValue, modifier = Modifier.fillMaxWidth())
    }
}
