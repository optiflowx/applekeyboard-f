package com.optiflowx.applekeyboard.views.global

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.core.enums.KeyboardType
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.clipboard.ClipboardKeyboardView
import com.optiflowx.applekeyboard.views.emoji.EmojiKeyboardView
import com.optiflowx.applekeyboard.views.normal.NormalKeyboardView
import com.optiflowx.applekeyboard.views.symbols.SymbolsKeyboardView

@Composable
fun PortraitKeyboard(
    viewModel: KeyboardViewModel,
) {
    val keyboardType = viewModel.keyboardType.collectAsState()
    val showTopView = (keyboardType.value != KeyboardType.Symbol)

    val viewWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        Modifier
            .mandatorySystemGesturesPadding()
    ) {
        Column {
            if (showTopView) KeyboardTopView(
                viewModel = viewModel,
//                locale = locale,
                keyboardType = keyboardType,
                viewWidth = viewWidth.value.toDouble()
            )

            when (keyboardType.value!!) {
                KeyboardType.Normal -> NormalKeyboardView(viewModel, viewWidth)

                KeyboardType.Symbol -> SymbolsKeyboardView(viewModel, viewWidth)

//                KeyboardType.Number -> NumberKeyboardView(viewModel, viewWidth)

//                KeyboardType.Phone -> PhoneNumberKeyboardView(viewModel, viewWidth)

                KeyboardType.Emoji -> EmojiKeyboardView(viewModel, viewWidth)

                KeyboardType.Clipboard -> ClipboardKeyboardView(viewModel, viewWidth)
            }

//            if (showBottomView)
            KeyboardBottomView(viewModel)
        }
    }
}