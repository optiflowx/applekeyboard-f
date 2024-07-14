package com.optiflowx.optikeysx.ime.components

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
import com.optiflowx.optikeysx.ime.viewmodel.KeyboardViewModel
import com.optiflowx.optikeysx.ime.views.clipboard.ClipboardKeyboardActionView
import com.optiflowx.optikeysx.ime.views.emoji.EmojiSearchView
import dev.patrickgold.jetpref.datastore.model.observeAsState


@Composable
fun KeyboardTopView(
    viewModel: KeyboardViewModel,
    viewWidth: Dp,
    topViewHeight: Int = 46,
    searchIconSize: Int = 20,
    textSize: Float = 16f,
) {
    val dH = 0
    val keyboardType = viewModel.keyboardType.collectAsState()
    val isSymbol = (keyboardType.value == KeyboardType.Symbol)
    val isPredictive = viewModel.prefs.isPredictive.observeAsState().value

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .layoutId("topView")
            .width(viewWidth)
            .height(
                (if (isSymbol) dH else {
                    if (keyboardType.value == KeyboardType.Normal) {
                        if (isPredictive) topViewHeight else 0
                    } else topViewHeight
                }).dp
            )
    ) {
        val boxScope = this@Box

        AnimatedContent(keyboardType.value, label = "KeyboardTopView") {
            if (it == KeyboardType.Emoji) {
                EmojiSearchView(viewModel, textSize, searchIconSize, boxScope)
            }

            if (it == KeyboardType.Normal && isPredictive) {
                SuggestionView(viewModel, textSize, boxScope)
            }

            if (it == KeyboardType.Clipboard) {
                ClipboardKeyboardActionView(viewModel, boxScope)
            }
        }
    }
}