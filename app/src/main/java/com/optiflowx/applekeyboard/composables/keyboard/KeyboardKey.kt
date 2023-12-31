package com.optiflowx.applekeyboard.composables.keyboard

import android.view.inputmethod.EditorInfo
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.common.appFontType
import com.optiflowx.applekeyboard.models.Key
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun KeyboardKey(key: Key, buttonWidth: Dp, viewModel: KeyboardViewModel) {
    val ctx = LocalContext.current
    val view = LocalView.current
    var keyValue by remember { mutableStateOf(key.value) }
    var action by remember { mutableIntStateOf(((ctx as IMEService).currentInputEditorInfo.imeOptions and EditorInfo.IME_MASK_ACTION)) }
    val colorScheme = MaterialTheme.colorScheme

    val isAllCaps = viewModel.isAllCaps.observeAsState().value
    val isNumberSymbol = viewModel.isNumberSymbol.observeAsState().value
    val actionButtonColor = viewModel.actionButtonColor.observeAsState().value
    val actionText = viewModel.actionButtonText.observeAsState().value
    val actionTextColor = viewModel.actionTextColor.observeAsState().value
    val isCapsLock = viewModel.isCapsLock.observeAsState().value

    val isSymbols: Boolean = key.id == "symbol"
    val isShift: Boolean = key.id == "shift"
    val isErase: Boolean = key.id == "erase"

    val soundID = when (key.id) {
        "erase" -> viewModel.soundPool?.load(ctx, R.raw.delete, 1)
        "action" -> viewModel.soundPool?.load(ctx, R.raw.ret, 1)
        "space" -> viewModel.soundPool?.load(ctx, R.raw.spacebar, 1)
        else -> viewModel.soundPool?.load(ctx, R.raw.standard, 1)
    }

    //Actions
    val language = viewModel.locale.collectAsState("en").value
    val actionDone = viewModel.actionDone.observeAsState().value
    val actionSend = viewModel.actionSend.observeAsState().value
    val defaultAction = viewModel.actionDefault.observeAsState().value
    val actionNext = viewModel.actionNext.observeAsState().value
    val actionSearch = viewModel.actionSearch.observeAsState().value
    val actionGo = viewModel.actionGo.observeAsState().value
    val fontType = viewModel.fontType.collectAsState("regular").value

    //State Handlers
    LaunchedEffect(language) {
        viewModel.actionDone.value = when (language) {
            "fr" -> "fait"
            "pt" -> "terminado"
            "es" -> "hecho"
            else -> "done"
        }

        viewModel.actionSend.value = when (language) {
            "fr" -> "lancer"
            "pt" -> "enviar"
            "es" -> "enviar"
            else -> "send"
        }

        viewModel.actionDefault.value = when (language) {
            "fr" -> "retour"
            "pt" -> "retornar"
            "es" -> "retorno"
            else -> "return"
        }

        viewModel.actionNext.value = when (language) {
            "fr" -> "à côté"
            "pt" -> "próximo"
            "es" -> "próximo"
            else -> "next"
        }

        viewModel.actionSearch.value = when (language) {
            "fr" -> "chercher"
            "pt" -> "procurar"
            "es" -> "buscar"
            else -> "search"
        }

        viewModel.actionGo.value = when (language) {
            "fr" -> "aller"
            "pt" -> "ir"
            "es" -> "ir"
            else -> "go"
        }
    }

    LaunchedEffect(isAllCaps) {
        isAllCaps.let {
            keyValue = if (it == true) key.value.uppercase() else key.value.lowercase()
        }
    }


    LaunchedEffect(key.id, language) {
        if (key.id == "action") {
            view.rootView.addOnLayoutChangeListener { view, _, _, _, _, _, _, _, _ ->
                view.isInLayout.let {
                    if (it) {
                        action =
                            (ctx as IMEService).currentInputEditorInfo.imeOptions and EditorInfo.IME_MASK_ACTION
                        when (action) {
                            EditorInfo.IME_ACTION_DONE -> {
                                viewModel.actionButtonColor.value = colorScheme.onSurface
                                viewModel.actionButtonText.value = actionDone
                                viewModel.actionTextColor.value = colorScheme.inversePrimary
                            }

                            EditorInfo.IME_ACTION_GO -> {
                                viewModel.actionButtonColor.value = colorScheme.onSurface
                                viewModel.actionButtonText.value = actionGo
                                viewModel.actionTextColor.value = colorScheme.inversePrimary
                            }

                            EditorInfo.IME_ACTION_SEARCH -> {
                                viewModel.actionButtonColor.value = colorScheme.onSurface
                                viewModel.actionButtonText.value = actionSearch
                                viewModel.actionTextColor.value = colorScheme.inversePrimary
                            }

                            EditorInfo.IME_ACTION_NEXT -> {
                                viewModel.actionButtonColor.value = colorScheme.onSurface
                                viewModel.actionButtonText.value = actionNext
                                viewModel.actionTextColor.value = colorScheme.inversePrimary
                            }

                            EditorInfo.IME_ACTION_SEND -> {
                                viewModel.actionButtonColor.value = colorScheme.onSurface
                                viewModel.actionButtonText.value = actionSend
                                viewModel.actionTextColor.value = colorScheme.inversePrimary
                            }

                            else -> {
                                viewModel.actionButtonColor.value = colorScheme.secondaryContainer
                                viewModel.actionButtonText.value = defaultAction
                                viewModel.actionTextColor.value = colorScheme.primary
                            }
                        }
                    }
                }
            }
        }
    }

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
                color = (if (isShift && isAllCaps!!) colorScheme.surface else colorScheme.secondaryContainer),
                buttonWidth = buttonWidth,
                id = key.id,
                onClick = { viewModel.onIKeyClick(key, ctx, soundID) },
            ) {
                Icon(
                    painter = this, "icon",
                    tint = if (isAllCaps!! && !isErase) Color.Black else colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth(0.55f)
                        .fillMaxHeight(0.48f),
                )
            }
        }
    } else {
        //All Other Text Keys
        (if (key.id == "123" || key.id == "ABC" || key.id == "action") {
            if (key.id == "action") actionButtonColor!! else colorScheme.secondaryContainer
        } else colorScheme.secondary).apply {
            KeyButton(
                color = this,
                buttonWidth = buttonWidth,
                id = key.id,
                onClick = { viewModel.onTKeyClick(key, ctx, soundID) }
            ) {
                Text(
                    text = (if (key.id == "ABC" || key.id == "space" || key.id == "action") {
                        if (key.id == "action") actionText!! else key.value
                    } else keyValue),
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    fontFamily = appFontType(fontType),
                    fontSize = TextUnit(
                        (if (key.id == "action" || key.id == "space") {
                            16f
                        } else 20f), TextUnitType.Sp
                    ),
                    color = if (key.id == "action") actionTextColor!! else colorScheme.primary,
                )
            }
        }
    }
}