package com.optiflowx.optikeysx.views.global

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.core.enums.KeyboardType
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import com.optiflowx.optikeysx.views.clipboard.ClipboardKeyboardActionView
import com.optiflowx.optikeysx.views.emoji.EmojiSearchView


@Composable
fun KeyboardTopView(
    viewModel: KeyboardViewModel,
    topViewHeight: Int = 48,
    searchIconSize: Int = 20,
    viewWidth: Dp,
    textSize: Float = 16f,
) {
    val dH = 0
    val keyboardType = viewModel.keyboardType.collectAsState()
    val isSymbol = (keyboardType.value == KeyboardType.Symbol)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .layoutId("topView")
            .width(viewWidth)
            .height((if (isSymbol) dH else topViewHeight).dp)
    ) {
        val boxScope = this

        AnimatedContent(keyboardType.value, label = "KeyboardTopView") {
            when (it) {
                KeyboardType.Emoji -> EmojiSearchView(viewModel, textSize, searchIconSize, boxScope)
                KeyboardType.Clipboard -> ClipboardKeyboardActionView(viewModel, boxScope)
                else -> SuggestionView(viewModel, textSize, boxScope)
            }
        }
    }
}