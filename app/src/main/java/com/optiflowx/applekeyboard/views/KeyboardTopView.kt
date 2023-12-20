package com.optiflowx.applekeyboard.views

import android.os.Build
import androidx.annotation.RequiresApi
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

@RequiresApi(Build.VERSION_CODES.R)
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
            if (it.value == KeyboardType.Emoji) {
                EmojiSearchView()
            } else {
                SuggestionView()
            }
        }
    }
}