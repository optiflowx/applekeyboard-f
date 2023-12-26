package com.optiflowx.applekeyboard.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.adapters.Key
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.ui.defaultFontFamily

@Composable
fun NumKeyboardKey(key: Key, buttonWidth: Dp) {
    val ctx = LocalContext.current
    val width = LocalConfiguration.current.screenWidthDp
    val colors = MaterialTheme.colors

    val viewModel = viewModel<KeyboardViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return KeyboardViewModel(width, colors) as T
            }
        }
    )

    val isPeriod: Boolean = key.id == "."
    val isErase: Boolean = key.id == "erase"

    //Erase and Period Keys
    KeyButton(
        elevation = (if (isErase || isPeriod) 0.dp else 3.dp),
        color = (if (isErase || isPeriod) Color.Transparent else colors.secondary),
        buttonWidth = buttonWidth,
        id = key.id,
        onClick = {
            if (isErase) viewModel.onIKeyClick(key, ctx)
            else viewModel.onNumKeyClick( key, ctx)
        },
        onDoubleClick = null
    ) {
        if (isErase) {
            Icon(
                painter = painterResource(R.drawable.deletebackward),
                "icons",
                tint = colors.primary,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(0.3f),
            )
        } else {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = key.id,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    fontFamily = defaultFontFamily,
                    style = TextStyle(
                        MaterialTheme.colors.primary,
                        TextUnit(26f, TextUnitType.Sp)
                    ),
                )
                Text(
                    text = key.value,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    fontFamily = defaultFontFamily,
                    style = TextStyle(
                        MaterialTheme.colors.primary,
                        TextUnit(12f, TextUnitType.Sp)
                    ),
                )
            }
        }
    }
}