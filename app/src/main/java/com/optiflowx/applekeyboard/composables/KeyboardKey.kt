package com.optiflowx.applekeyboard.composables

import android.view.inputmethod.EditorInfo
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.adapters.Key
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.ui.defaultFontFamily

@Composable
fun KeyboardKey(key: Key, buttonWidth: Dp) {
    val ctx = LocalContext.current
    val haptic = LocalHapticFeedback.current
    var keyValue by remember { mutableStateOf(key.value) }
    var action by remember { mutableIntStateOf(((ctx as IMEService).currentInputEditorInfo.imeOptions and EditorInfo.IME_MASK_ACTION)) }
    val width = LocalConfiguration.current.screenWidthDp
    val colors = MaterialTheme.colors

    val viewModel = viewModel<KeyboardViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return KeyboardViewModel(width, colors) as T
            }
        }
    )

    val isAllCaps = viewModel.isAllCaps.observeAsState().value
    val isNumberSymbol = viewModel.isNumberSymbol.observeAsState().value
    val actionButtonColor = viewModel.actionButtonColor.observeAsState().value
    val actionText = viewModel.actionButtonText.observeAsState().value
    val actionTextColor = viewModel.actionTextColor.observeAsState().value
    val isCapsLock = viewModel.isCapsLock.observeAsState().value


    // Declaring and Initializing
//    val player = MediaPlayer.create(
//        ctx, (when (key.id) {
//            "action" -> {
//                R.raw.ret
//            }
//
//            "space" -> {
//                R.raw.spacebar
//            }
//
//            "erase" -> {
//                R.raw.delete
//            }
//
//            else -> {
//                R.raw.standard
//            }
//        })
//    )


    val isSymbols: Boolean = key.id == "symbol"
    val isShift: Boolean = key.id == "shift"
    val isErase: Boolean = key.id == "erase"

    LaunchedEffect(isAllCaps) {
        isAllCaps.let {
            keyValue = if (it == true) key.value.uppercase() else key.value.lowercase()
        }
    }

    if (key.id == "action") {
        LocalView.current.rootView.addOnLayoutChangeListener { view, _, _, _, _, _, _, _, _ ->
            view.isInLayout.let {
//                Log.d("AppleKeyboardView", "isInLayout: $it")
                if (it) {
                    action =
                        (ctx as IMEService).currentInputEditorInfo.imeOptions and EditorInfo.IME_MASK_ACTION
//                    Log.d("Action:${key.id}", action.toString())
                    when (action) {
                        EditorInfo.IME_ACTION_DONE -> {
                            viewModel.actionButtonColor.value = colors.onSurface
                            viewModel.actionButtonText.value = "done"
                            viewModel.actionTextColor.value = colors.primaryVariant
                        }

                        EditorInfo.IME_ACTION_GO -> {
                            viewModel.actionButtonColor.value = colors.onSurface
                            viewModel.actionButtonText.value = "go"
                            viewModel.actionTextColor.value = colors.primaryVariant
                        }

                        EditorInfo.IME_ACTION_SEARCH -> {
                            viewModel.actionButtonColor.value = colors.onSurface
                            viewModel.actionButtonText.value = "search"
                            viewModel.actionTextColor.value = colors.primaryVariant
                        }

                        EditorInfo.IME_ACTION_NEXT -> {
                            viewModel.actionButtonColor.value = colors.onSurface
                            viewModel.actionButtonText.value = "next"
                            viewModel.actionTextColor.value = colors.primaryVariant
                        }

                        EditorInfo.IME_ACTION_SEND -> {
                            viewModel.actionButtonColor.value = colors.onSurface
                            viewModel.actionButtonText.value = "send"
                            viewModel.actionTextColor.value = colors.primaryVariant
                        }

                        else -> {
                            viewModel.actionButtonColor.value = colors.secondaryVariant
                            viewModel.actionButtonText.value = "return"
                            viewModel.actionTextColor.value = colors.primary
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
            else {
                if (isAllCaps == true) {
                    painterResource(R.drawable.shift_fill)
                } else painterResource(R.drawable.shift)
            }
        } else if (isSymbols) {
            if (isNumberSymbol == true) {
                painterResource(R.drawable.num)
            } else painterResource(R.drawable.sym)
        } else {
            painterResource(R.drawable.deletebackward)
        }).apply {
            KeyButton(
                color = (if(isShift && isAllCaps!!) colors.surface else colors.secondaryVariant),
                buttonWidth = buttonWidth,
                id = key.id,
                onClick = { viewModel.onIKeyClick(haptic, key, ctx) },
                onDoubleClick = if (isShift) { { viewModel.onIDoubleClick(haptic, key) } } else null
            ) {
                Icon(
                    painter = this, "icon",
                    tint = if(isAllCaps!! && !isErase) Color.Black else MaterialTheme.colors.primary,
                    modifier = Modifier
                        .fillMaxWidth(0.55f)
                        .fillMaxHeight(0.48f),
                )
            }
        }
    } else {
        //All Other Text Keys
        (if (key.id == "123" || key.id == "ABC" || key.id == "action") {
            if (key.id == "action") actionButtonColor!! else colors.secondaryVariant
        } else colors.secondary).apply {
            KeyButton(
                color = this,
                buttonWidth = buttonWidth,
                id = key.id,
                onClick = { viewModel.onTKeyClick(haptic, key, ctx) },
                onDoubleClick = null
            ) {
                Text(
                    text = (if (key.id == "ABC" || key.id == "space" || key.id == "action") {
                        if (key.id == "action") actionText!! else key.value
                    } else keyValue),
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    fontFamily = defaultFontFamily,
                    style = TextStyle(if (key.id == "action") actionTextColor!! else MaterialTheme.colors.primary, TextUnit(20f, TextUnitType.Sp)),
                )
            }
        }
    }
}