package com.optiflowx.applekeyboard.views

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.ui.defaultFontFamily
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoSearchTextField
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.MagnifyingGlass
import io.github.alexzhirkevich.cupertino.rememberCupertinoSearchTextFieldState
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray

//@Preview
@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun EmojiSearchView(viewModel: KeyboardViewModel) {

    val text = remember { mutableStateOf("") }
    val state = rememberCupertinoSearchTextFieldState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(state.isFocused) {
        if(state.isFocused) {
            focusRequester.requestFocus().let {
                focusRequester.captureFocus()
            }
        } else {
            focusRequester.freeFocus()
        }
    }

    Log.d("EmojiSearchView", "isFocused: ${state.isFocused}")
    Log.d("EmojiSearchView", "isEmojiSearch: ${viewModel.isEmojiSearch.value}")
//    Log.d("EmojiSearchView", "FocusReq: ${}")

    CupertinoSearchTextField(
        value = text.value,
        state = state,
        modifier = Modifier
            .height(45.dp)
            .fillMaxHeight()
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                viewModel.isEmojiSearch.value = focusState.isFocused },
        placeholder = {
            Text(
                "Search Emoji",
                color = CupertinoColors.systemGray(isSystemInDarkTheme()),
                fontFamily = defaultFontFamily,
                fontSize = TextUnit(17f, TextUnitType.Sp),
            )
        },
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontFamily = defaultFontFamily,
            fontSize = TextUnit(17f, TextUnitType.Sp),
        ),
        leadingIcon = {
            CupertinoIcon(
                CupertinoIcons.Outlined.MagnifyingGlass,
                contentDescription = "icon",
                tint = CupertinoColors.systemGray(isSystemInDarkTheme()),
                modifier = Modifier.height(20.dp)
            )
        },
        onValueChange = {
            text.value = it
        },
        keyboardOptions = KeyboardOptions(
            imeAction = androidx.compose.ui.text.input.ImeAction.Search
        )
    ) {
    }
}