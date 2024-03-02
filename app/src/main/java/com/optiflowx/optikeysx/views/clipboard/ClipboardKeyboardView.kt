package com.optiflowx.optikeysx.views.clipboard

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.optiflowx.optikeysx.core.utils.appFontType
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray

@Composable
fun ClipboardKeyboardView(
    viewModel: KeyboardViewModel,
    viewWidth: Dp,
    viewHeight: Dp = 200.dp,
) {
    val context = LocalContext.current

    val clipDataList = viewModel.clipData.observeAsState().value?.reversed()

    val fontType = viewModel.prefs.keyboardFontType.observeAsState().value

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(viewHeight)
            .width(viewWidth)
    ) {
        if (!clipDataList.isNullOrEmpty()) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(3),
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
            viewModel.keyboardLocale.emptyClipboard(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = CupertinoColors.systemGray(isSystemInDarkTheme()),
                fontFamily = appFontType(fontType),
                fontSize = TextUnit(15f, TextUnitType.Sp).nonScaledSp,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            modifier = Modifier.padding(15.dp),
        )
    }
}