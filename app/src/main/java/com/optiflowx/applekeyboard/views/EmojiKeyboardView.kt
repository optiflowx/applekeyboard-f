package com.optiflowx.applekeyboard.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.composables.keyboard.EmojiItem
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.ui.mediumFontFamily
import com.optiflowx.applekeyboard.utils.frequentlyUsedEmoji
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmojiKeyboardView(viewModel: KeyboardViewModel) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
//    val context = LocalContext.current

    val frequentEmojis = viewModel.frequentlyUsedEmojiDao?.getAllEmojis()?.observeAsState()?.value?.reversed()

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
    val freqViewPort = (screenWidth * 0.76f).dp
    val defaultViewPort = (screenWidth).dp
    val isESearch = viewModel.isEmojiSearch.observeAsState()

    AnimatedContent(isESearch, label = "Alternating Views") {
        if (it.value!!) NormalKeyboardView(viewModel)
        else {
            Column {
                HorizontalPager(
                    state = pagerState,
                    pageSpacing = 10.dp,
                    pageSize = PageSize.Fixed(if (pagerState.currentPage == 0) freqViewPort else defaultViewPort),
                    modifier = Modifier.height(220.dp),
                ) { page ->
                    emojiViewPager[page].forEach { (title, emojis) ->
                        Column {
                            Text(
                                text = title.uppercase(),
                                fontSize = TextUnit(13f, TextUnitType.Sp),
                                fontFamily = mediumFontFamily,
                                color = CupertinoColors.systemGray(isSystemInDarkTheme()),
                                modifier = Modifier.padding(start = 15.dp, bottom = 3.dp)
                            )
                            Surface(
                                modifier = Modifier.height(200.dp),
                                color = Color.Transparent
                            ) {
                                if (page == 0 && frequentEmojis != null) {
                                    LazyVerticalGrid(columns = GridCells.Fixed(5)) {
                                        frequentEmojis.forEach { data ->
                                            item("Key:$data") {
                                                EmojiItem(data.emoji, viewModel, title)
                                            }
                                        }
                                    }
                                }

                                if (page != 0) {
                                    LazyVerticalGrid(columns = GridCells.Fixed(6)) {
                                        emojis.forEach { emoji ->
                                            item("$page$emoji") {
                                                EmojiItem(emoji, viewModel, title)
                                            }
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
    }
}