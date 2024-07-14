package com.optiflowx.optikeysx.ime.views.emoji

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.core.utils.KeyboardLocale
import com.optiflowx.optikeysx.core.utils.appFontType
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ime.viewmodel.KeyboardViewModel
import dev.patrickgold.jetpref.datastore.model.observeAsState
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
    val currentFocus = LocalFocusManager.current

    val fontType = viewModel.prefs.keyboardFontType.observeAsState().value
    val locale = viewModel.keyboardData.collectAsState().value.locale
    val keyboardLocale = KeyboardLocale(locale)

//    val context = LocalContext.current as IMEService

    LaunchedEffect(state.isFocused) {
        if (state.isFocused) {
            currentFocus.clearFocus(true).also {
                focusRequester.requestFocus()
            }
        } else focusRequester.freeFocus()
    }



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
                    keyboardLocale.searchEmoji(),
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