package com.optiflowx.applekeyboard.views.emoji

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.core.preferences.PrefsConstants
import com.optiflowx.applekeyboard.core.preferences.rememberPreference
import com.optiflowx.applekeyboard.core.utils.KeyboardLocale
import com.optiflowx.applekeyboard.core.utils.appFontType
import com.optiflowx.applekeyboard.core.utils.nonScaledSp
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
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
fun EmojiSearchView(viewModel: KeyboardViewModel, textSize: Float, searchIconSize: Int, boxScope: BoxScope) {

    val (text, onTextChange) = remember { mutableStateOf("") }
    val state = rememberCupertinoSearchTextFieldState()
    val focusRequester = remember { FocusRequester() }

    val fontType  by rememberPreference(PrefsConstants.FONT_TYPE_KEY, "Regular")

    val locale  by rememberPreference(PrefsConstants.LOCALE_KEY, "English")

    LaunchedEffect(state.isFocused) {
        if (state.isFocused) {
            focusRequester.requestFocus().let {
                focusRequester.captureFocus()
            }
        } else focusRequester.freeFocus()
    }

    val keyboardLocale = KeyboardLocale()

    boxScope.apply {
        CupertinoSearchTextField(
            value = text,
            onValueChange = onTextChange,
            state = state,
            modifier = Modifier
                .padding(vertical = 2.5.dp)
                .matchParentSize()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState -> viewModel.updateIsEmojiSearch(focusState.isFocused) },
            placeholder = {
                Text(
                    keyboardLocale.searchEmoji(locale),
                    style = TextStyle(
                        color = CupertinoColors.systemGray(isSystemInDarkTheme()),
                        fontFamily = appFontType(fontType),
                        fontSize = TextUnit(textSize, TextUnitType.Sp).nonScaledSp,
                        platformStyle = PlatformTextStyle(includeFontPadding = false),
                    )
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontFamily = appFontType(fontType),
                fontSize = TextUnit(textSize, TextUnitType.Sp).nonScaledSp,
                platformStyle = PlatformTextStyle(includeFontPadding = false),
            ),
            leadingIcon = {
                CupertinoIcon(
                    CupertinoIcons.Outlined.MagnifyingGlass,
                    contentDescription = "icon",
                    tint = CupertinoColors.systemGray(isSystemInDarkTheme()),
                    modifier = Modifier.height(searchIconSize.dp)
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            )
        )
    }
}