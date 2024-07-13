package com.optiflowx.optikeysx.screens.home.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import com.optiflowx.optikeysx.R
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTextField
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.SectionItem

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
            CupertinoText(stringResource(R.string.test_keyboard), style = titleTextStyle)
        },
        caption = {
            CupertinoText(
                text = stringResource(R.string.test_keyboard_description),
                style = descTextStyle
            )
        }
    ) {
        SectionItem {
            CupertinoTextField(
                value = value,
                enabled = true,
                onValueChange = onValueChange,
                interactionSource = interactionSource,
                placeholder = {
                    CupertinoText(stringResource(R.string.type_here))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
            )
        }
    }
}