package com.optiflowx.applekeyboard.composables.keyboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.ui.iosEmojiFontFamily
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun EmojiItem(emoji: String, viewModel: KeyboardViewModel, title: String) {
    // context
    val context = LocalContext.current

    Text(
        emoji,
        fontSize = TextUnit(24f, TextUnitType.Sp),
        fontFamily = iosEmojiFontFamily,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(5.dp)
            .clickable(
                onClick = { viewModel.onEmojiClick(context, emoji, title) },
            ),
    )
}