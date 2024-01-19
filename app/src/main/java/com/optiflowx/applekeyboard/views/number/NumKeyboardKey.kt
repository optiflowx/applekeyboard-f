package com.optiflowx.applekeyboard.views.number

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.composables.keyboard.KeyButton
import com.optiflowx.applekeyboard.models.Key
import com.optiflowx.applekeyboard.storage.PreferencesConstants
import com.optiflowx.applekeyboard.utils.appFontType
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun NumKeyboardKey(key: Key, buttonWidth: Dp, viewModel: KeyboardViewModel) {
    val ctx = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme
    val isPeriod: Boolean = key.id == "."
    val isErase: Boolean = key.id == "erase"

    val fontType = viewModel.preferences.getFlowPreference(PreferencesConstants.FONT_TYPE_KEY, "Regular").collectAsState(
        "Regular").value

    val soundID = when(key.id) {
        "erase" -> viewModel.soundPool?.load(ctx, R.raw.delete, 1)
        else -> viewModel.soundPool?.load(ctx, R.raw.standard, 1)
    }


    KeyButton(
        color = (if (isErase || isPeriod) Color.Transparent else colorScheme.secondary),
        buttonWidth = buttonWidth,
        id = key.id,
        pressState = {},
        onClick = {
            if (isErase) viewModel.onIKeyClick(key, ctx)
            else viewModel.onNumKeyClick( key, ctx)

            viewModel.playSound(soundID)
            viewModel.vibrate()
        },
    ) {
        if (isErase) {
            Icon(
                painter = painterResource(R.drawable.deletebackward),
                "icons",
                tint = colorScheme.primary,
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
                    fontFamily = appFontType(fontType),
                    style = TextStyle(
                        MaterialTheme.colorScheme.primary,
                        TextUnit(26f, TextUnitType.Sp)
                    ),
                )
                Text(
                    text = key.value,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    fontFamily = appFontType(fontType),
                    style = TextStyle(
                        MaterialTheme.colorScheme.primary,
                        TextUnit(12f, TextUnitType.Sp)
                    ),
                )
            }
        }
    }
}