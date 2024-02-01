package com.optiflowx.applekeyboard.views.global

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.core.enums.KeyboardType
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.clipboard.ClipboardKeyboardActionView
import com.optiflowx.applekeyboard.views.emoji.EmojiSearchView

@Composable
fun KeyboardTopView(
    viewModel: KeyboardViewModel,
    keyboardType: State<KeyboardType?>,
    topViewHeight: Int = 48,
    searchIconSize : Int = 20,
    viewWidth: Double,
    textSize: Float = 16f,
) {
    Box(
        modifier = Modifier
            .width(viewWidth.dp)
            .height(topViewHeight.dp)
            .fillMaxWidth()
    ) {
        AnimatedContent(keyboardType.value, label = "KeyboardTopView") {
            when (it) {
                KeyboardType.Emoji -> EmojiSearchView(viewModel, textSize, searchIconSize)
//                KeyboardType.Number -> NumberKeyboardActionView(locale)
//                KeyboardType.Phone -> NumberKeyboardActionView(locale)
                KeyboardType.Clipboard -> ClipboardKeyboardActionView(viewModel, topViewHeight)
                else -> SuggestionView(viewModel, viewWidth, textSize)
            }
        }
    }
}