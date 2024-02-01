package com.optiflowx.applekeyboard.views.normal

import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.core.data.Key
import com.optiflowx.applekeyboard.core.preferences.PrefsConstants
import com.optiflowx.applekeyboard.core.preferences.rememberPreference
import com.optiflowx.applekeyboard.core.services.IMEService
import com.optiflowx.applekeyboard.core.utils.KeyboardLocale
import com.optiflowx.applekeyboard.ui.keyboard.EraseButton
import com.optiflowx.applekeyboard.ui.keyboard.KeyButton
import com.optiflowx.applekeyboard.utils.appFontType
import com.optiflowx.applekeyboard.utils.nonScaledSp
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun KeyboardKey(key: Key, viewModel: KeyboardViewModel) {
    val ctx = LocalContext.current
    val view = LocalView.current

    val keyboardLocale = KeyboardLocale()
    val colorScheme = MaterialTheme.colorScheme

    val isSymbols = key.id == "symbol"
    val isShift = key.id == "shift"
    val isErase = key.id == "delete"
    val isEmoji = key.id == "emoji"

    val colorA = colorScheme.onSurface
    val colorB = colorScheme.inversePrimary
    val colorD = colorScheme.primary
    val colorC = colorScheme.secondaryContainer

    val isAllCaps = viewModel.isAllCaps.collectAsState().value
    val isNumberSymbol = viewModel.isNumberSymbol.collectAsState().value
    val isCapsLock = viewModel.isCapsLock.collectAsState().value
    val buttonColor = viewModel.keyActionButtonColor.collectAsState().value
    val textColor = viewModel.keyActionTextColor.collectAsState().value
    val text = viewModel.keyActionText.collectAsState().value

    var keyValue by rememberSaveable { mutableStateOf(key.value) }
    val fontType by rememberPreference(PrefsConstants.FONT_TYPE_KEY, "Regular")
    val locale by rememberPreference(PrefsConstants.LOCALE_KEY, "English")

    LaunchedEffect(isAllCaps) {
        keyValue = if (isAllCaps) {
            key.value.uppercase()
        } else key.value.lowercase()
    }

    DisposableEffect(key.id) {
        if (key.id == "action") {
            view.addOnLayoutChangeListener { view, _, _, _, _, _, _, _, _ ->
                view.isInLayout.let {visible ->
                    val action =
                        (ctx as IMEService).currentInputEditorInfo.imeOptions and EditorInfo.IME_MASK_ACTION
                    if (visible) {
                        when (action) {
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
            }
        }

        onDispose {
            view.removeOnLayoutChangeListener { view, _, _, _, _, _, _, _, _ ->
                Log.v("KEYBOARD INFO", "onDispose: actionView Listener")
            }
        }
    }

    val width = LocalConfiguration.current.screenWidthDp
    val widthFactor = 0.086f
    val specialWidthFactor = 0.13f
    val popupWidth = if (
        key.id == "." || key.id == "," || key.id == "?" || key.id == "'" || key.id == "\""
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
                tint = colorScheme.primary,
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
                if (isSystemInDarkTheme()) R.drawable.emoji_fill else R.drawable.emoji_outline
            )
        }).apply {
            KeyButton(
                color = (if (isShift && isAllCaps) colorScheme.surface else colorC),
                id = key.id,
                showPopup = false,
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
                color = this,
                id = key.id,
                text = keyValue,
                popupWidth = popupWidth,
                showPopup = !(key.id == "123" || key.id == "ABC" || key.id == "action" || key.id == "space"),
                onClick = {
                    viewModel.onTKeyClick(key, ctx, text).let {
                        viewModel.playSound(key)
                        viewModel.vibrate()
                    }
                }
            ) {
                Text(
                    text = (if (key.id == "ABC" || key.id == "space" || key.id == "action") {
                        if (key.id == "action") keyboardLocale.action(text, locale)
                        else key.value
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