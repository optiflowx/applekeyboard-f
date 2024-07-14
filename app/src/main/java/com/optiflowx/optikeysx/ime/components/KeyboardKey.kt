package com.optiflowx.optikeysx.ime.components

import android.view.inputmethod.EditorInfo
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.model.Key
import com.optiflowx.optikeysx.core.utils.KeyboardLocale
import com.optiflowx.optikeysx.core.utils.appFontType
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ime.viewmodel.KeyboardViewModel
import dev.patrickgold.jetpref.datastore.model.observeAsState

@Composable
fun KeyboardKey(key: Key, viewModel: KeyboardViewModel) {
    val ctx = LocalContext.current
    val locale = viewModel.keyboardData.collectAsState().value.locale
    val keyboardLocale = KeyboardLocale(locale)
    val colorScheme = MaterialTheme.colorScheme

    val isSymbols = key.id == "symbol"
    val isShift = key.id == "shift"
    val isErase = key.id == "delete"
    val isEmoji = key.id == "emoji"

    val colorA = colorScheme.onSurface
    val colorB = colorScheme.inversePrimary
    val colorC = colorScheme.secondaryContainer
    val colorD = colorScheme.primary


    val isAllCaps = viewModel.prefs.isAllCaps.observeAsState().value
    val isNumberSymbol = viewModel.isNumberSymbol.collectAsState().value
    val isCapsLock = viewModel.prefs.isCapsLock.observeAsState().value
    val buttonColor = viewModel.keyActionButtonColor.collectAsState().value
    val textColor = viewModel.keyActionTextColor.collectAsState().value
    val text = viewModel.keyActionText.collectAsState().value
    val keyboardData = viewModel.keyboardData.collectAsState().value
    val enterAction = keyboardData.enterAction

    var keyValue by rememberSaveable { mutableStateOf(key.value) }
    val fontType = viewModel.prefs.keyboardFontType.observeAsState().value

    LaunchedEffect(isAllCaps) {
        keyValue = if (isAllCaps) {
            key.value.uppercase()
        } else key.value.lowercase()
    }

    LaunchedEffect(key.id, keyboardData) {
        if (key.id == "action") {
            when (enterAction) {
                EditorInfo.IME_ACTION_DONE -> {
                    viewModel.updateIMEActions(colorA, colorB, "done")
                }

                EditorInfo.IME_ACTION_GO -> {
                    viewModel.updateIMEActions(colorA, colorB, "go")
                }

                EditorInfo.IME_ACTION_SEARCH -> {
                    viewModel.updateIMEActions(colorA, colorB, "search")
                }

                EditorInfo.IME_ACTION_NEXT -> {
                    viewModel.updateIMEActions(colorA, colorB, "next")
                }

                EditorInfo.IME_ACTION_SEND -> {
                    viewModel.updateIMEActions(colorA, colorB, "send")
                }

                else -> viewModel.updateIMEActions(colorC, colorD, "return")
            }
        }
    }

    val width = LocalConfiguration.current.screenWidthDp
    val widthFactor = 0.086f
    val specialWidthFactor = 0.1275f
    val popupWidth = if (
        key.id == "." || key.id == "," || key.id == "?" || key.id == "!" || key.id == "'"
    ) (width * specialWidthFactor) else width * widthFactor

    //Erase and Shift Keys
    if (isErase) {
        EraseButton(
            color = colorC,
            id = key.id,
            onClick = {
                viewModel.playSound(key)
                viewModel.vibrate()
            },
            onRepeatableClick = { viewModel.onIKeyClick(key, ctx) }
        ) {
            Icon(
                painter = painterResource(R.drawable.deletebackward),
                contentDescription = "icon",
                modifier = Modifier
                    .fillMaxHeight(0.54f)
                    .fillMaxWidth(0.54f),
                tint = if (it) MaterialTheme.colorScheme.tertiaryContainer else colorD,
            )
        }
    } else if (isShift || isSymbols || isEmoji) {
        (if (isShift) {
            if (isCapsLock) painterResource(R.drawable.capslockfill)
            else painterResource(
                if (isAllCaps) R.drawable.shift_fill else R.drawable.shift
            )
        } else if (isSymbols) {
            painterResource(
                if (isNumberSymbol) R.drawable.num else R.drawable.sym
            )
        } else {
            painterResource(
                if (isSystemInDarkTheme()) R.drawable.emoji_fill else
                    R.drawable.emoji_outline
            )
        }).apply {
            KeyButton(
                color = (if (isShift && isAllCaps) colorScheme.surface else colorC),
                key = key,
                showPopup = false,
                prefs = viewModel.prefs,
                onClick = {
                    viewModel.onIKeyClick(key, ctx).let {
                        viewModel.playSound(key)
                        viewModel.vibrate()
                    }
                }
            ) {
                Icon(
                    painter = this,
                    contentDescription = "icon",
                    modifier = Modifier
                        .fillMaxHeight(0.54f)
                        .fillMaxWidth(0.54f),
                    tint = if (isAllCaps && !isEmoji && !isSymbols)
                        Color.Black else colorScheme.primary,
                )
            }
        }
    } else {
        //All Other Text Keys
        (if (key.id == "123" || key.id == "ABC" || key.id == "action") {
            if (key.id == "action") buttonColor else colorC
        } else colorScheme.secondary).apply {
            KeyButton(
                color = this@apply,
                key = key,
                prefs = viewModel.prefs,
                popupText = keyValue,
                popupWidth = popupWidth,
                showPopup = !(key.id == "123" || key.id == "ABC" || key.id == "action" || key.id == "space"),
                onClick = {
                    viewModel.onTKeyClick(key, ctx).let {
                        viewModel.playSound(key)
                        viewModel.vibrate()
                    }
                }
            ) {
                Text(
                    text = (if (key.id == "ABC" || key.id == "space" || key.id == "action") {
                        if (key.id == "action") keyboardLocale.action(text) else key.value
                    } else keyValue),
                    maxLines = 1,
                    style = TextStyle(
                        fontFamily = appFontType(fontType),
                        fontSize = (if (key.id == "123" || key.id == "ABC" || key.id == "action" || key.id == "space")
                            15.sp else 22.5.sp).nonScaledSp,
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    color = if (key.id == "action") textColor else colorD,
                )
            }
        }
    }
}