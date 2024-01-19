package com.optiflowx.applekeyboard.views.normal

import android.view.inputmethod.EditorInfo
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.composables.keyboard.KeyButton
import com.optiflowx.applekeyboard.models.Key
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.storage.PreferencesConstants
import com.optiflowx.applekeyboard.utils.KeyboardLocale
import com.optiflowx.applekeyboard.utils.appFontType
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun KeyboardKey(key: Key, buttonWidth: Dp, viewModel: KeyboardViewModel) {
    val ctx = LocalContext.current
    val view = LocalView.current
    var keyValue by rememberSaveable { mutableStateOf(key.value) }
    var action by rememberSaveable { mutableIntStateOf(((ctx as IMEService).currentInputEditorInfo.imeOptions and EditorInfo.IME_MASK_ACTION)) }
    val colorScheme = MaterialTheme.colorScheme

    val isAllCaps = viewModel.isAllCaps.observeAsState().value
    val isNumberSymbol = viewModel.isNumberSymbol.observeAsState().value
    val isCapsLock = viewModel.isCapsLock.observeAsState().value

    val isSymbols: Boolean = key.id == "symbol"
    val isShift: Boolean = key.id == "shift"
    val isErase: Boolean = key.id == "erase"

    val keyboardLocale = KeyboardLocale()

    val soundID = when (key.id) {
        "erase" -> viewModel.soundPool?.load(ctx, R.raw.delete, 1)
        "action" -> viewModel.soundPool?.load(ctx, R.raw.ret, 1)
        "space" -> viewModel.soundPool?.load(ctx, R.raw.spacebar, 1)
        else -> viewModel.soundPool?.load(ctx, R.raw.standard, 1)
    }

    var actionButtonColor by remember { mutableStateOf(colorScheme.secondaryContainer) }

    var actionTextColor by remember { mutableStateOf(colorScheme.inversePrimary) }

    var actionText by rememberSaveable { mutableStateOf("return") }

    val fontType =
        viewModel.preferences.getFlowPreference(PreferencesConstants.FONT_TYPE_KEY, "Regular")
            .collectAsState(
                "Regular"
            ).value

    val locale = viewModel.preferences.getFlowPreference(PreferencesConstants.LOCALE_KEY, "ENGLISH")
        .collectAsState(
            "ENGLISH"
        ).value

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

    var pressed by remember { mutableStateOf(false) }
    var popupPosition by remember { mutableStateOf(Offset.Zero) }
    var popupSize by remember { mutableStateOf(IntSize.Zero) }

    //Erase and Shift Keys
    if (isShift || isErase || isSymbols) {
        (if (isShift) {
            if (isCapsLock!!) painterResource(R.drawable.capslockfill)
            else if (isAllCaps == true) {
                painterResource(R.drawable.shift_fill)
            } else painterResource(R.drawable.shift)
        } else if (isSymbols) {
            if (isNumberSymbol == true) {
                painterResource(R.drawable.num)
            } else painterResource(R.drawable.sym)
        } else painterResource(R.drawable.deletebackward)).apply {
            KeyButton(
                color = (if (isShift && isAllCaps!!) colorScheme.surface else colorScheme.secondaryContainer).copy(
                    alpha = 0.8f
                ),
                buttonWidth = buttonWidth,
                id = key.id,
                pressState = {},
                onClick = {
                    viewModel.onIKeyClick(key, ctx)
                    viewModel.playSound(soundID)
                    viewModel.vibrate()
                },
            ) {
                Icon(
                    painter = this,
                    contentDescription = "icon",
                    modifier = Modifier
                        .fillMaxWidth(0.55f)
                        .fillMaxHeight(0.55f),
                    tint = if (isAllCaps!! && !isErase) Color.Black else colorScheme.primary,
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
                buttonWidth = buttonWidth,
                id = key.id,
                pressState = { pressed = it },
                modifier = Modifier.onGloballyPositioned {
                    popupPosition = it.positionInRoot()
                    popupSize = it.size
                },
                onClick = {
                    viewModel.onTKeyClick(key, ctx, actionText)
                    viewModel.playSound(soundID)
                    viewModel.vibrate()
                }
            ) {
                Text(
                    text = (if (key.id == "ABC" || key.id == "space" || key.id == "action") {
                        if (key.id == "action") keyboardLocale.action(actionText, locale)
                        else key.value
                    } else keyValue),
                    modifier = Modifier.absolutePadding(bottom = 5.dp),
                    fontFamily = appFontType(fontType),
                    fontSize = TextUnit(
                        (if (key.id == "123" || key.id == "ABC" || key.id == "action" || key.id == "space") {
                            13f
                        } else 20f), TextUnitType.Sp
                    ),
                    color = if (key.id == "action") actionTextColor else colorScheme.primary,
                )
            }
        }
    }
}