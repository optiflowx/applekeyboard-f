package com.optiflowx.optikeysx.screens.home

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTextField
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun TestKeyboardSection(
    titleTextStyle: TextStyle,
    descTextStyle: TextStyle,
) {

    val (value, onValueChange) = remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    CupertinoSection(
        title = {
            CupertinoText("TEST KEYBOARD", style = titleTextStyle)
        },
        caption = {
            CupertinoText(
                text = "This section is for trying out the keyboard.",
                style = descTextStyle
            )
        }
    ) {
        this.item {
            CupertinoTextField(
                value = value,
                enabled = true,
                onValueChange = onValueChange,
                modifier = Modifier.padding(it),
                interactionSource = interactionSource,
                placeholder = {
                    CupertinoText("Input Test")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
            )
        }
    }
}