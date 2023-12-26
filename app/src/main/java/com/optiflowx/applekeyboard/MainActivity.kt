package com.optiflowx.applekeyboard

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoTextField
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.Surface
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.AppleLogo
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray5
import splitties.systemservices.inputMethodManager

//@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalCupertinoApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                CupertinoScaffold(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    contentWindowInsets = WindowInsets.safeDrawing,
                    topBar = {
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
                    },
                ) {
                    //Disclaimer Block
                    //Caution Block
                    HomeScreen()
                }
                isSystemInDarkTheme()
            }
        }
    }
}

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun HomeScreen() {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            val ctx = LocalContext.current
            val (text, setValue) = remember { mutableStateOf(TextFieldValue("")) }
            val (text2, setValue2) = remember { mutableStateOf(TextFieldValue("")) }
            val (text3, setValue3) = remember { mutableStateOf(TextFieldValue("")) }


            CupertinoButton(modifier = Modifier.fillMaxWidth(), onClick = {
                ctx.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
            }) {
                Text(text = "Enable IME")
            }
            Spacer(modifier = Modifier.height(16.dp))
            CupertinoButton(modifier = Modifier.fillMaxWidth(), onClick = {
                inputMethodManager.showInputMethodPicker()
            }) {
                Text(text = "Select IME")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Text", color = Color.White)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = (CupertinoColors.systemGray5(isSystemInDarkTheme()))
            ) {

                CupertinoTextField(
                    value = text,
                    onValueChange = setValue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Number", color = Color.White)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = (CupertinoColors.systemGray5(isSystemInDarkTheme()))
            ) {

                CupertinoTextField(
                    value = text2,
                    onValueChange = setValue2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Phone", color = Color.White)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = (CupertinoColors.systemGray5(isSystemInDarkTheme()))
            ) {

                CupertinoTextField(
                    value = text3,
                    onValueChange = setValue3,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Phone
                    )
                )
            }
        }
    }
}
