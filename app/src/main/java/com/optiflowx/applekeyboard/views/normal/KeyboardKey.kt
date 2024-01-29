package com.optiflowx.applekeyboard.views.normal

import android.view.inputmethod.EditorInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.core.data.Key
import com.optiflowx.applekeyboard.core.preferences.PreferencesConstants
import com.optiflowx.applekeyboard.core.services.IMEService
import com.optiflowx.applekeyboard.core.utils.KeyboardLocale
import com.optiflowx.applekeyboard.ui.keyboard.KeyButton
import com.optiflowx.applekeyboard.utils.appFontType
import com.optiflowx.applekeyboard.utils.nonScaledSp
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun KeyboardKey(key: Key, viewModel: KeyboardViewModel) {
    val ctx = LocalContext.current
    val view = LocalView.current
    var keyValue by rememberSaveable { mutableStateOf(key.value) }
    var action by rememberSaveable { mutableIntStateOf(((ctx as IMEService).currentInputEditorInfo.imeOptions and EditorInfo.IME_MASK_ACTION)) }
    val colorScheme = MaterialTheme.colorScheme

    val isSymbols = key.id == "symbol"
    val isShift = key.id == "shift"
    val isErase = key.id == "delete"
    val isEmoji = key.id == "emoji"
    val isAllCaps = viewModel.isAllCaps.observeAsState(false).value
    val isNumberSymbol = viewModel.isNumberSymbol.observeAsState(false).value
    val isCapsLock = viewModel.isCapsLock.observeAsState(false).value

    val keyboardLocale = KeyboardLocale()

    var actionButtonColor by remember { mutableStateOf(colorScheme.secondaryContainer) }

    var actionTextColor by remember { mutableStateOf(colorScheme.inversePrimary) }

    var actionText by rememberSaveable { mutableStateOf("return") }

    val fontType = viewModel.preferences
        .getFlowPreference(PreferencesConstants.FONT_TYPE_KEY, "Regular")
        .collectAsStateWithLifecycle("Regular").value

    val locale = viewModel.preferences
        .getFlowPreference(PreferencesConstants.LOCALE_KEY, "English")
        .collectAsStateWithLifecycle("English").value

    LaunchedEffect(isAllCaps) {
        isAllCaps.let {
            keyValue = if (it == true) key.value.uppercase() else key.value.lowercase()
        }
    }

    LaunchedEffect(key.id) {
        if (key.id == "action") {
            view.rootView.addOnLayoutChangeListener { view, _, _, _, _, _, _, _, _ ->
                view.isInLayout.let {
                    if (it) {
                        action =
                            (ctx as IMEService).currentInputEditorInfo.imeOptions and EditorInfo.IME_MASK_ACTION
                        when (action) {
                            EditorInfo.IME_ACTION_DONE -> {
                                actionButtonColor = colorScheme.onSurface
                                actionTextColor = colorScheme.inversePrimary
                                actionText = "done"
                            }

                            EditorInfo.IME_ACTION_GO -> {
                                actionButtonColor = colorScheme.onSurface
                                actionTextColor = colorScheme.inversePrimary
                                actionText = "go"
                            }

                            EditorInfo.IME_ACTION_SEARCH -> {
                                actionButtonColor = colorScheme.onSurface
                                actionTextColor = colorScheme.inversePrimary
                                actionText = "search"
                            }

                            EditorInfo.IME_ACTION_NEXT -> {
                                actionButtonColor = colorScheme.onSurface
                                actionTextColor = colorScheme.inversePrimary
                                actionText = "next"
                            }

                            EditorInfo.IME_ACTION_SEND -> {
                                actionButtonColor = colorScheme.onSurface
                                actionTextColor = colorScheme.inversePrimary
                                actionText = "send"
                            }

                            else -> {
                                actionButtonColor = colorScheme.secondaryContainer
                                actionTextColor = colorScheme.primary
                                actionText = "return"
                            }
                        }
                    }
                }
            }
        }
    }

    //Erase and Shift Keys
    if (isShift || isErase || isSymbols || isEmoji) {
        (if (isShift) {
            if (isCapsLock!!) painterResource(R.drawable.capslockfill)
            else painterResource(
                if (isAllCaps == true) R.drawable.shift_fill else R.drawable.shift
            )
        } else if (isSymbols) {
            painterResource(
                if (isNumberSymbol == true) R.drawable.num else R.drawable.sym
            )
        } else if (isEmoji) {
            painterResource(
                if (isSystemInDarkTheme()) R.drawable.emoji_fill else R.drawable.emoji_outline
            )
        } else painterResource(R.drawable.deletebackward)).apply {
            KeyButton(
                color = (if (isShift && isAllCaps!!) colorScheme.surface else colorScheme.secondaryContainer).copy(
                    alpha = 0.8f
                ),
                id = key.id,
                showPopup = false,
                onRepeatableClick = { viewModel.onIKeyClick(key, ctx) },
                onSingleClick = {
                    viewModel.playSound(key)
                    viewModel.vibrate()
                }
            ) {
                Icon(
                    painter = this,
                    contentDescription = "icon",
                    modifier = Modifier.fillMaxHeight(0.54f).fillMaxWidth(0.54f),
                    tint = if (isAllCaps!! && !isErase && !isEmoji && !isSymbols)
                            Color.Black else colorScheme.primary,
                )
            }
        }
    } else {
        //All Other Text Keys
        (if (key.id == "123" || key.id == "ABC" || key.id == "action") {
            if (key.id == "action") actionButtonColor else colorScheme.secondaryContainer
        } else colorScheme.secondary).apply {
            KeyButton(
                color = this,
                id = key.id,
                text = keyValue,
                showPopup = !(key.id == "123" || key.id == "ABC" || key.id == "action" || key.id == "space"),
                onRepeatableClick = { viewModel.onTKeyClick(key, ctx, actionText) },
                onSingleClick = {
                    viewModel.playSound(key)
                    viewModel.vibrate()
                }
            ) {
                Text(
                    text = (if (key.id == "ABC" || key.id == "space" || key.id == "action") {
                        if (key.id == "action") keyboardLocale.action(actionText, locale)
                        else key.value
                    } else keyValue),
                    maxLines = 1,
                    style = TextStyle(
                        fontFamily = appFontType(fontType),
                        fontSize = TextUnit(
                            (if (key.id == "123" || key.id == "ABC" || key.id == "action" || key.id == "space") {
                                15f
                            } else 22.5f), TextUnitType.Sp
                        ).nonScaledSp,
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    color = if (key.id == "action") actionTextColor else colorScheme.primary,
                )
            }
        }
    }
}