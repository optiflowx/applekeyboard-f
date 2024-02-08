package com.optiflowx.optikeysx.views.emoji

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.core.model.frequentlyUsedEmoji
import com.optiflowx.optikeysx.core.utils.appFontType
import com.optiflowx.optikeysx.core.utils.handleTitle
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmojiKeyboardView(
    viewModel: KeyboardViewModel,
    viewWidth: Dp,
    viewportHeight: Dp = 230.dp,
    cellCount: Int = 6,
) {
    
    val defaultViewPort = (viewWidth)
    val freqViewPort = (viewWidth * 0.76f)
    val locale = viewModel.keyboardData.collectAsState().value.locale
//    val isESearch = viewModel.isEmojiSearch.collectAsState()
    val frequentEmojis = viewModel.frequentlyUsedEmojis.observeAsState().value?.reversed()
    val fontType = viewModel.prefs.keyboardFontType.observeAsState().value

    val emojiViewPager = arrayListOf(
        hashMapOf("Frequently Used" to listOf()),
        hashMapOf("Smileys & People" to frequentlyUsedEmoji.smilesAndPeople),
        hashMapOf("Animals & Nature" to frequentlyUsedEmoji.animalsAndNature),
        hashMapOf("Food & Drink" to frequentlyUsedEmoji.foodAndDrinks),
        hashMapOf("Activities" to frequentlyUsedEmoji.activities),
        hashMapOf("Travel & Places" to frequentlyUsedEmoji.travelAndPlaces),
        hashMapOf("Objects" to frequentlyUsedEmoji.objects),
        hashMapOf("Symbols" to frequentlyUsedEmoji.symbols),
        hashMapOf("Flags" to frequentlyUsedEmoji.flags),
    )
    val pagerState = rememberPagerState(pageCount = { emojiViewPager.size }, initialPage = 0)

//    if (isESearch.value) StandardKeyboardView(viewModel, viewWidth)
//    else
    Column(
        Modifier.width(viewWidth)
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 10.dp,
            pageSize = PageSize.Fixed(if (pagerState.currentPage == 0) freqViewPort else defaultViewPort),
            modifier = Modifier.height(viewportHeight),
        ) { page ->
            emojiViewPager[page].forEach { (title, emojis) ->
                Column {
                    Text(
                        text = handleTitle(title, locale).uppercase(),
                        modifier = Modifier.padding(start = 15.dp, bottom = 3.dp),
                        style = TextStyle(
                            fontFamily = appFontType(fontType),
                            color = CupertinoColors.systemGray(isSystemInDarkTheme()),
                            fontSize = TextUnit(13f, TextUnitType.Sp).nonScaledSp,
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        )
                    )

                    Surface(
                        modifier = Modifier.height((viewportHeight.value - 20).dp),
                        color = Color.Transparent
                    ) {
                        LazyVerticalGrid(columns = GridCells.Fixed(cellCount)) {
                            if (page == 0 && frequentEmojis != null) {
                                frequentEmojis.forEach { data ->
                                    item("Key:$data") {
                                        EmojiItem(data.emoji, viewModel, title)
                                    }
                                }
                            } else emojis.forEach { emoji ->
                                item("$page$emoji") {
                                    EmojiItem(emoji, viewModel, title)
                                }
                            }
                        }
                    }
                }
            }
        }
        EmojiPagerActionView(pagerState, viewModel)
    }
}

