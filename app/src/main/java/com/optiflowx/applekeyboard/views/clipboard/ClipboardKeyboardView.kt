package com.optiflowx.applekeyboard.views.clipboard

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.optiflowx.applekeyboard.core.preferences.PreferencesConstants
import com.optiflowx.applekeyboard.core.utils.KeyboardLocale
import com.optiflowx.applekeyboard.utils.appFontType
import com.optiflowx.applekeyboard.utils.nonScaledSp
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray

@Composable
fun ClipboardKeyboardView(viewModel: KeyboardViewModel) {
    val context = LocalContext.current

    val fontType = viewModel.preferences.getFlowPreference(
        PreferencesConstants.FONT_TYPE_KEY, "Regular"
    ).collectAsStateWithLifecycle("Regular").value

    val locale = viewModel.preferences.getFlowPreference(
        PreferencesConstants.LOCALE_KEY, "English"
    ).collectAsStateWithLifecycle("English").value

    val keyboardLocale = KeyboardLocale()

    val clipDataList = viewModel.clipData.observeAsState().value?.reversed()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        if (!clipDataList.isNullOrEmpty()) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(100.dp),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalItemSpacing = 5.dp,
                modifier = Modifier.matchParentSize()
            ) {
                for (index in 0 until clipDataList.count()) {
                    val text = clipDataList[index].text

                    item("clipboard_item_$index::string->${text}") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.onPrimary,
                                    RoundedCornerShape(5.dp)
                                )
                                .clickable(
                                    indication = LocalIndication.current,
                                    interactionSource = remember { MutableInteractionSource() },
                                    role = Role.Button
                                ) { viewModel.pasteTextFromClipboard(text, context) }
                        ) {
                            Text(
                                text = text,
                                textAlign = TextAlign.Center,
                                minLines = 1,
                                softWrap = true,
                                style = TextStyle(
                                    fontSize = 15.sp.nonScaledSp,
                                    fontFamily = appFontType(fontType),
                                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                                ),
                                modifier = Modifier
                                    .padding(5.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        } else Text(
            keyboardLocale.emptyClipboard(locale),
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = CupertinoColors.systemGray(isSystemInDarkTheme()),
                fontFamily = appFontType("Regular"),
                fontSize = TextUnit(15f, TextUnitType.Sp).nonScaledSp,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            modifier = Modifier.padding(15.dp),
        )
    }
}