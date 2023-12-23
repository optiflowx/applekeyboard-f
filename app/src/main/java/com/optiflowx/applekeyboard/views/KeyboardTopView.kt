package com.optiflowx.applekeyboard.views

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
import com.optiflowx.applekeyboard.utils.KeyboardType

@Composable
fun KeyboardTopView(keyboardType: State<KeyboardType?>) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Box(
        Modifier
            .width(screenWidth.dp)
            .height(48.dp)
            .fillMaxWidth()
    ) {
        AnimatedContent(keyboardType, label = "TopView") {
            when (it.value) {
                KeyboardType.Emoji -> EmojiSearchView()
                KeyboardType.Number -> NumberKeyboardActionView()
                KeyboardType.Phone -> NumberKeyboardActionView()
                else -> SuggestionView()
            }
        }
    }
}