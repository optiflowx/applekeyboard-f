package com.optiflowx.applekeyboard.views.global

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.core.enums.KeyboardType
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.clipboard.ClipboardKeyboardActionView
import com.optiflowx.applekeyboard.views.emoji.EmojiSearchView
import com.optiflowx.applekeyboard.views.number.NumberKeyboardActionView

@Composable
fun KeyboardTopView(viewModel: KeyboardViewModel, locale: String, keyboardType: State<KeyboardType?>) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Box(
        Modifier
            .width(screenWidth.dp)
            .height(48.dp)
            .fillMaxWidth()
    ) {
        AnimatedContent(keyboardType.value, label = "KeyboardTopView") {
            when (it) {
                KeyboardType.Emoji -> EmojiSearchView(viewModel)
                KeyboardType.Number -> NumberKeyboardActionView(locale)
                KeyboardType.Phone -> NumberKeyboardActionView(locale)
                KeyboardType.Clipboard -> ClipboardKeyboardActionView(viewModel)
                else -> SuggestionView(viewModel)
            }
        }
    }
}