package com.optiflowx.optikeysx.views.clipboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.core.enums.KeyboardType
import com.optiflowx.optikeysx.core.preferences.PrefsConstants
import com.optiflowx.optikeysx.core.preferences.rememberPreference
import com.optiflowx.optikeysx.core.utils.KeyboardLocale
import com.optiflowx.optikeysx.core.utils.appFontType
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue
import io.github.alexzhirkevich.cupertino.theme.systemGray
import io.github.alexzhirkevich.cupertino.theme.systemRed

@Composable
fun ClipboardKeyboardActionView(viewModel: KeyboardViewModel, boxScope: BoxScope) {
    val keyboardLocale = KeyboardLocale()
    val locale by rememberPreference(PrefsConstants.LOCALE_KEY, "English")

    val clipDataList = viewModel.clipData.observeAsState().value

    boxScope.apply {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
        ) {
            Text(
                keyboardLocale.clipboard(locale),
                style = TextStyle(
                    fontFamily = appFontType("Bold"),
                    color = CupertinoColors.systemGray(isSystemInDarkTheme()),
                    fontSize = TextUnit(18f, TextUnitType.Sp).nonScaledSp,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
            )

            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    keyboardLocale.clear(locale),
                    style = TextStyle(
                        color = if (clipDataList.isNullOrEmpty()) CupertinoColors.systemGray(
                            isSystemInDarkTheme()
                        ) else CupertinoColors.systemRed,
                        fontFamily = appFontType("Bold"),
                        fontSize = TextUnit(14f, TextUnitType.Sp).nonScaledSp,
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable(
                            enabled = !clipDataList.isNullOrEmpty(),
                        ) { viewModel.clearClipboard(clipDataList)},
                )

                Text(
                    keyboardLocale.back(locale),
                    style = TextStyle(
                        color = CupertinoColors.systemBlue,
                        fontFamily = appFontType("Bold"),
                        fontSize = TextUnit(14f, TextUnitType.Sp).nonScaledSp,
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable(
                            onClick = { viewModel.onUpdateKeyboardType(KeyboardType.Normal) }
                        ),
                )
            }
        }
    }
}