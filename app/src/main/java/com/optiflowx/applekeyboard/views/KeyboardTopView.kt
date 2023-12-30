package com.optiflowx.applekeyboard.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.utils.KeyboardType

@Composable
fun KeyboardTopView(viewModel: KeyboardViewModel) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val keyboardType = viewModel.keyboardType.observeAsState()

    Box(
        Modifier
            .width(screenWidth.dp)
            .height(48.dp)
            .fillMaxWidth()
    ) {
        AnimatedContent(keyboardType, label = "TopView") {
            when (it.value) {
                KeyboardType.Emoji -> EmojiSearchView(viewModel)
                KeyboardType.Number -> NumberKeyboardActionView(viewModel)
                KeyboardType.Phone -> NumberKeyboardActionView(viewModel)
                else -> SuggestionView(viewModel)
            }
        }
    }
}