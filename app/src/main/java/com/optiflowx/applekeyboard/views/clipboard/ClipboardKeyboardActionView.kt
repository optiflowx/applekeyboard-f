package com.optiflowx.applekeyboard.views.clipboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.optiflowx.applekeyboard.core.enums.KeyboardType
import com.optiflowx.applekeyboard.core.preferences.PrefsConstants
import com.optiflowx.applekeyboard.core.preferences.rememberPreference
import com.optiflowx.applekeyboard.core.utils.KeyboardLocale
import com.optiflowx.applekeyboard.utils.appFontType
import com.optiflowx.applekeyboard.utils.nonScaledSp
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue
import io.github.alexzhirkevich.cupertino.theme.systemGray
import io.github.alexzhirkevich.cupertino.theme.systemRed

@Composable
fun ClipboardKeyboardActionView(viewModel: KeyboardViewModel, topViewHeight: Int) {
    val keyboardLocale = KeyboardLocale()
    val locale by rememberPreference(PrefsConstants.LOCALE_KEY, "English")

    val clipDataList = viewModel.clipData.observeAsState().value

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(topViewHeight.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
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
            modifier = Modifier.wrapContentWidth().fillMaxHeight(),
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