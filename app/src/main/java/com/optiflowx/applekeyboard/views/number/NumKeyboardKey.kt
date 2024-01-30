package com.optiflowx.applekeyboard.views.number

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.core.data.Key
import com.optiflowx.applekeyboard.core.preferences.PreferencesConstants
import com.optiflowx.applekeyboard.ui.keyboard.KeyButton
import com.optiflowx.applekeyboard.utils.appFontType
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun NumKeyboardKey(key: Key, viewModel: KeyboardViewModel) {
    val ctx = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme
    val isPeriod: Boolean = key.id == "period"
    val isErase: Boolean = key.id == "delete"

    val fontType =
        viewModel.preferences.getFlowPreference(PreferencesConstants.FONT_TYPE_KEY, "Regular")
            .collectAsStateWithLifecycle("Regular").value

    KeyButton(
        color = (if (isErase || isPeriod) Color.Transparent else colorScheme.secondary),
        id = key.id,
        showPopup = false,
        onRepeatableClick = {
            if (isErase) viewModel.onIKeyClick(key, ctx)
            else viewModel.onNumKeyClick(key, ctx)
        },
        onSingleClick = {
            viewModel.playSound(key)
            viewModel.vibrate()
        }
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
                    text = if(isPeriod) "." else key.id,
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