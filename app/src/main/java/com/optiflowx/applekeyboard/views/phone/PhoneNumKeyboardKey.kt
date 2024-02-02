package com.optiflowx.applekeyboard.views.phone

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.sp
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.core.data.Key
import com.optiflowx.applekeyboard.core.preferences.PrefsConstants
import com.optiflowx.applekeyboard.core.preferences.rememberPreference
import com.optiflowx.applekeyboard.core.utils.KeyboardLocale
import com.optiflowx.applekeyboard.ui.keyboard.EraseButton
import com.optiflowx.applekeyboard.ui.keyboard.KeyButton
import com.optiflowx.applekeyboard.core.utils.appFontType
import com.optiflowx.applekeyboard.core.utils.nonScaledSp
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray

@Composable
fun PhoneNumKeyboardKey(key: Key, viewModel: KeyboardViewModel) {
    val ctx = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme
    val isSwitch: Boolean = key.id == "switch"
    val isErase: Boolean = key.id == "delete"

    //Make it global
    val keyboardLocale = KeyboardLocale()
    val isPhoneSymbols = viewModel.isPhoneSymbol.collectAsState().value

    val fontType by rememberPreference(PrefsConstants.FONT_TYPE_KEY, "Regular")
    val locale by rememberPreference(PrefsConstants.LOCALE_KEY, "English")

    val keysToDisable = (key.id == "1" || key.id == "2"
            || key.id == "3" || key.id == "5"
            || key.id == "8")

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
        color = (if (isSwitch) Color.Transparent
        else if (keysToDisable && isPhoneSymbols) colorScheme.outline
        else colorScheme.secondary),
        id = key.id,
        showPopup = false,
        enabled = !(isPhoneSymbols && keysToDisable),
        onClick = {
            if (isSwitch) viewModel.onPhoneSymbol()
            else viewModel.onNumKeyClick(key, ctx)

            viewModel.playSound(key)
            viewModel.vibrate()
        },
    ) {
        if (isSwitch) {
            Icon(
                painter = (if (isPhoneSymbols) painterResource(R.drawable.num)
                else painterResource(R.drawable.sym)),
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
                if (key.value == keyboardLocale.wait(locale) || key.value == keyboardLocale.pause(
                        locale
                    )
                    || key.value == "*" || key.value == "#"
                    || key.value == "+"
                ) {
                    Text(
                        text = when (key.value) {
                            keyboardLocale.wait(locale) -> keyboardLocale.wait(locale)
                            keyboardLocale.pause(locale) -> keyboardLocale.pause(locale)
                            else -> key.value
                        },
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        fontFamily = appFontType(fontType),
                        style = TextStyle(
                            MaterialTheme.colorScheme.primary,
                            TextUnit(26f, TextUnitType.Sp)
                        ),
                    )
                } else {
                    (if (isPhoneSymbols) {
                        CupertinoColors.systemGray(isSystemInDarkTheme())
                    } else MaterialTheme.colorScheme.primary).apply {
                        Text(
                            text = key.id,
                            fontWeight = FontWeight.Light,
                            textAlign = TextAlign.Center,
                            fontFamily = appFontType(fontType),
                            style = TextStyle(this, 28.sp.nonScaledSp),
                        )
                        Text(
                            text = key.value,
                            fontWeight = FontWeight.Light,
                            textAlign = TextAlign.Center,
                            fontFamily = appFontType(fontType),
                            style = TextStyle(this, 12.sp.nonScaledSp),
                        )
                    }
                }
            }
        }
    }
}