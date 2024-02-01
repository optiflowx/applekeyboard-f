package com.optiflowx.applekeyboard.views.global

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalConfiguration
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

//Should be stable!

@Composable
fun KeyboardView(viewModel: KeyboardViewModel) {
    val config = LocalConfiguration.current

    val keyboardType = viewModel.keyboardType.collectAsState()

    val orientation = rememberSaveable(config.orientation) {
        mutableIntStateOf(config.orientation)
    }

//    LaunchedEffect(Unit) {
//        while (true) {
//            val editor = (context as IMEService).currentInputEditorInfo
//
//            when (editor.inputType and EditorInfo.IME_MASK_ACTION) {
//                InputType.number.value -> {
//                    viewModel.keyboardType.value = KeyboardType.Number
//                }
//
//                InputType.phone.value -> {
//                    viewModel.keyboardType.value = KeyboardType.Phone
//                }
//
//                else -> {
//                    viewModel.keyboardType.value = when (keyboardType.value) {
//                        KeyboardType.Emoji, KeyboardType.Symbol, KeyboardType.Clipboard -> keyboardType.value
//                        else -> KeyboardType.Normal
//                    }
//                }
//            }
//            this.run {
//                yield()
//                delay(2500)
//            }
//        }
//    }

    if (orientation.intValue == ORIENTATION_PORTRAIT) {
        PortraitKeyboard(keyboardType, viewModel)
    } else LandscapeKeyboard(keyboardType, viewModel)
}