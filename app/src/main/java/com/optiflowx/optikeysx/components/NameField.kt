package com.optiflowx.optikeysx.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.core.Constants.NAME_LABEL
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTextField
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import kotlinx.coroutines.job

@Composable
fun NameField(
    name: TextFieldValue,
    onNameValueChange: (newValue: TextFieldValue) -> Unit
) {
    val focusRequester = FocusRequester()

    Box(
        modifier = Modifier
            .padding(10.dp)
            .border(
                width = 1.dp,
                color = CupertinoTheme.colorScheme.separator,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        CupertinoTextField(
            value = name,
            onValueChange = { newValue ->
                onNameValueChange(newValue)
            },
            placeholder = {
                CupertinoText(
                    text = NAME_LABEL
                )
            },

            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .focusRequester(focusRequester).padding(
                    vertical = 10.dp
                )

        )
    }


    LaunchedEffect(Unit) {
        coroutineContext.job.invokeOnCompletion {
            focusRequester.requestFocus()
        }
    }
}