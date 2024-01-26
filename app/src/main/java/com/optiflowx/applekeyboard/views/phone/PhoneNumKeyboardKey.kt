package com.optiflowx.applekeyboard.views.phone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import com.optiflowx.applekeyboard.core.utils.KeyboardLocale
import com.optiflowx.applekeyboard.ui.keyboard.KeyButton
import com.optiflowx.applekeyboard.utils.appFontType
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun PhoneNumKeyboardKey(key: Key, buttonWidth: Dp, viewModel: KeyboardViewModel) {
    val ctx = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme
    val isSwitch: Boolean = key.id == "switch"
    val isErase: Boolean = key.id == "erase"

    //Make it global
    val keyboardLocale = KeyboardLocale()
    val isPhoneSymbols = viewModel.isPhoneSymbol.observeAsState().value!!

    val fontType = viewModel.preferences.getFlowPreference(PreferencesConstants.FONT_TYPE_KEY, "Regular").collectAsStateWithLifecycle(
        "Regular").value

    val locale = viewModel.preferences.getFlowPreference(PreferencesConstants.LOCALE_KEY, "English").collectAsStateWithLifecycle(
        "English").value

//    var soundID: Int? = null

//    LaunchedEffect(true) {
//        this.launch(Dispatchers.IO) {
//            soundID = when (key.id) {
//                "erase" -> viewModel.soundPool.load(ctx, R.raw.delete, 1)
//                else -> viewModel.soundPool.load(ctx, R.raw.standard, 1)
//            }
//        }
//    }

    //Erase and Switch Keys
    KeyButton(
        color = (if (isErase || isSwitch) Color.Transparent else colorScheme.secondary),
        buttonWidth = buttonWidth,
        id = key.id,
        showPopup = false,
        onRepeatableClick = {
            if (isErase) viewModel.onIKeyClick(key, ctx)
            else if (isSwitch) viewModel.onPhoneSymbol()
            else viewModel.onNumKeyClick(key, ctx)
        },
        onSingleClick = {
//            viewModel.playSound(soundID)
            viewModel.vibrate()
        },
    ) {
        if (isSwitch || isErase) {
            Icon(
                painter = (if (isErase) painterResource(R.drawable.deletebackward)
                else if (isPhoneSymbols) painterResource(R.drawable.num)
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
}