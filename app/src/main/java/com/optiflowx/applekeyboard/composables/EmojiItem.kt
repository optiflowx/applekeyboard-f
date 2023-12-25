package com.optiflowx.applekeyboard.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.ui.iosEmojiFontFamily
import kotlinx.coroutines.launch

@Composable
fun EmojiItem(
    emoji: String,
    viewModel: KeyboardViewModel
) {
    // context
    val context = LocalContext.current
    // scope
    val scope = rememberCoroutineScope()

    Text(
        emoji,
        fontSize = TextUnit(24f, TextUnitType.Sp),
        fontFamily = iosEmojiFontFamily,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(5.dp)
            .clickable(
                onClick = {
                    scope.launch {
                        viewModel.onEmojiClick(context, emoji, scope)
                    }
                },
            ),
    )
}