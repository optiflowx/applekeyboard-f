package com.optiflowx.optikeysx.ime.views.emoji

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ime.theme.iosEmojiFontFamily
import com.optiflowx.optikeysx.ime.viewmodel.KeyboardViewModel

@Composable
fun EmojiItem(emoji: String, viewModel: KeyboardViewModel, title: String) {
    val context = LocalContext.current

    Text(
        emoji,
        style = TextStyle(
            fontSize = TextUnit(27f, TextUnitType.Sp).nonScaledSp,
            fontFamily = iosEmojiFontFamily,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(5.dp)
            .clickable(
                onClick = { viewModel.onEmojiClick(context, emoji, title) },
            ),
    )
}