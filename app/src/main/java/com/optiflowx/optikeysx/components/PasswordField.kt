package com.optiflowx.optikeysx.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.core.Constants.PASSWORD_LABEL
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTextField
import io.github.alexzhirkevich.cupertino.CupertinoTextFieldDefaults
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Eye
import io.github.alexzhirkevich.cupertino.icons.outlined.EyeSlash
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme

@Composable
fun PasswordField(
    password: TextFieldValue,
    onPasswordValueChange: (newValue: TextFieldValue) -> Unit
) {
    var passwordIsVisible by remember { mutableStateOf(false) }
    val hazeState = remember { HazeState() }

    Box(
        modifier = Modifier.padding(10.dp)
            .border(
                width = 1.dp,
                color = CupertinoTheme.colorScheme.separator,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        CupertinoTextField(
            value = password,
            colors = CupertinoTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Red,
                focusedContainerColor = Color.Black.copy(
                    alpha = 0.6f
                ),
            ),
            modifier = Modifier
                .hazeChild(
                    state = hazeState,
                    style = HazeDefaults.style(tint = Color.Red.copy(alpha = 0.1f), blurRadius = 8.dp),
                ),
            onValueChange = { newValue ->
                onPasswordValueChange(newValue)
            },
            placeholder = {
                CupertinoText(
                    text = PASSWORD_LABEL
                )
            },
            singleLine = true,
            visualTransformation = if (passwordIsVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                val icon = if (passwordIsVisible) {
                    CupertinoIcons.Default.Eye
                } else {
                    CupertinoIcons.Default.EyeSlash
                }

                CupertinoIcon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(38.dp)
                        .padding(8.dp)
                        .clickable {
                        passwordIsVisible = !passwordIsVisible
                    }
                )
            }
        )
    }


}