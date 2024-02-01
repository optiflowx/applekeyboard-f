package com.optiflowx.applekeyboard.views.global

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.optiflowx.applekeyboard.core.enums.KeyboardType
import com.optiflowx.applekeyboard.core.preferences.PrefsConstants
import com.optiflowx.applekeyboard.core.preferences.rememberPreference
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.clipboard.ClipboardKeyboardView
import com.optiflowx.applekeyboard.views.emoji.EmojiKeyboardView
import com.optiflowx.applekeyboard.views.normal.NormalKeyboardView
import com.optiflowx.applekeyboard.views.number.NumberKeyboardView
import com.optiflowx.applekeyboard.views.phone.PhoneNumberKeyboardView
import com.optiflowx.applekeyboard.views.symbols.SymbolsKeyboardView

@Composable
fun PortraitKeyboard(
    keyboardType: State<KeyboardType?>,
    viewModel: KeyboardViewModel,
) {

    val showTopView = (keyboardType.value != KeyboardType.Symbol)

    val showBottomView = (keyboardType.value != KeyboardType.Number
            && keyboardType.value != KeyboardType.Phone)

    val fontType  by rememberPreference(PrefsConstants.FONT_TYPE_KEY, "Regular")

    val locale  by rememberPreference(PrefsConstants.LOCALE_KEY, "English")

    val viewWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        Modifier
            .mandatorySystemGesturesPadding()
    ) {
        Column {
            if (showTopView) KeyboardTopView(
                viewModel = viewModel,
                locale = locale,
                keyboardType = keyboardType,
                viewWidth = viewWidth.value.toDouble()
            )

            when (keyboardType.value!!) {
                KeyboardType.Normal -> NormalKeyboardView(viewModel, viewWidth)

                KeyboardType.Symbol -> SymbolsKeyboardView(viewModel, viewWidth)

                KeyboardType.Number -> NumberKeyboardView(viewModel, viewWidth)

                KeyboardType.Phone -> PhoneNumberKeyboardView(viewModel, viewWidth)

                KeyboardType.Emoji -> EmojiKeyboardView(viewModel, viewWidth)

                KeyboardType.Clipboard -> ClipboardKeyboardView(viewModel, viewWidth)
            }

            if (showBottomView) KeyboardBottomView(viewModel, locale, fontType)
        }
    }
}