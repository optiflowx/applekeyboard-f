package com.optiflowx.optikeysx.views.number

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.data.Key
import com.optiflowx.optikeysx.core.preferences.PrefsConstants
import com.optiflowx.optikeysx.core.preferences.rememberPreference
import com.optiflowx.optikeysx.ui.keyboard.EraseButton
import com.optiflowx.optikeysx.ui.keyboard.KeyButton
import com.optiflowx.optikeysx.core.utils.appFontType
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel

@Composable
fun NumKeyboardKey(key: Key, viewModel: KeyboardViewModel) {
    val ctx = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme
    val isPeriod: Boolean = key.id == "period"
    val isErase: Boolean = key.id == "delete"
    val fontType  by rememberPreference(PrefsConstants.FONT_TYPE_KEY, "Regular")


    if (isErase) {
        EraseButton(
            color = Color.Transparent,
            id = key.id,
            onClick = {
                viewModel.playSound(key)
                viewModel.vibrate()
            },
            onRepeatableClick = { viewModel.onIKeyClick(key, ctx) }
        ) {
            Icon(
                painter = painterResource(R.drawable.deletebackward),
                "icons",
                tint = colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(0.3f),
            )
        }
    } else KeyButton(
        color = (if (isPeriod) Color.Transparent else colorScheme.secondary),
        id = key.id,
        showPopup = false,
        onClick = {
            viewModel.onNumKeyClick(key, ctx)
            viewModel.playSound(key)
            viewModel.vibrate()
        }
    ) {
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