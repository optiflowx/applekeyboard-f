package com.optiflowx.applekeyboard.composables.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.cupertino.CupertinoTextField
import io.github.alexzhirkevich.cupertino.Surface
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray5

@Composable
fun TextFieldView(title: String,fieldValue: TextFieldValue, setValue: (TextFieldValue) -> Unit) {
    Spacer(Modifier.height(8.dp))
    Text(text = title, color = Color.White)
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = CupertinoColors.systemGray5(true)
    ) {
        CupertinoTextField(
            value = fieldValue,
            onValueChange = setValue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textStyle = TextStyle(Color.White),
            keyboardOptions = KeyboardOptions(
                imeAction = when(title) {
                    "Text" -> ImeAction.Done
                    "General" -> ImeAction.None
                    "URL" -> ImeAction.Go
                    "Search" -> ImeAction.Search
                    "Next" -> ImeAction.Next
                    "Number" -> ImeAction.Done
                    "Phone" -> ImeAction.Done
                    else -> ImeAction.Done
                },
                keyboardType = (when(title) {
                    "Text" -> KeyboardType.Text
                    "Number" -> KeyboardType.Number
                    "Phone" -> KeyboardType.Phone
                    else -> KeyboardType.Text
                })
            )
        )
    }
    Spacer(Modifier.height(8.dp))
}