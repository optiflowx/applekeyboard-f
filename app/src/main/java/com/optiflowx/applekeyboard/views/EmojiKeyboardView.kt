package com.optiflowx.applekeyboard.views

import android.content.Context
import android.util.Log
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.composables.EmojiItem
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.ui.mediumFontFamily
import com.optiflowx.applekeyboard.utils.emojiData
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmojiKeyboardView() {
    val colors = MaterialTheme.colors
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val viewModel = viewModel<KeyboardViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return KeyboardViewModel(screenWidth, colors) as T
            }
        }
    )

//    val frequentEmojis = viewModel.appSettings.value?.frequentlyUsedEmojis!!

    val emojiViewPager = arrayListOf<HashMap<String, PersistentList<String>>>(
        hashMapOf("Frequently Used" to persistentListOf()),
        hashMapOf("Smileys & People" to emojiData.smilesAndPeople),
        hashMapOf("Animals & Nature" to emojiData.animalsAndNature),
        hashMapOf("Food & Drink" to emojiData.foodAndDrinks),
        hashMapOf("Activities" to emojiData.activities),
        hashMapOf("Travel & Places" to emojiData.travelAndPlaces),
        hashMapOf("Objects" to emojiData.objects),
        hashMapOf("Symbols" to emojiData.symbols),
        hashMapOf("Flags" to emojiData.flags),
    )

    val pagerState = rememberPagerState(
        pageCount = { emojiViewPager.size },
        initialPage = 0,
    )

    Log.d("Emoji List Tracker", "Emoji List: $emojiViewPager")

//    LaunchedEffect(Unit) {
//        while (pagerState.currentPage < pagerState.pageCount - 1) {
//            yield()
//            kotlinx.coroutines.delay(3000)
//            pagerState.animateScrollToPage(pagerState.currentPage + 1)
//        }
//    }

    val freqViewPort = (screenWidth * 0.8f).dp
    val defaultViewPort = (screenWidth).dp

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
                        modifier = Modifier.height(195.dp),
                        color = Color.Transparent
                    ) {
                        LazyVerticalGrid(columns = GridCells.Fixed(6)) {
                            emojis.forEach { emoji ->
                                item("$page$emoji") {
                                    EmojiItem(emoji, viewModel)
                                }
                            }
                        }
                    }
                }
            }
        }
        EmojiPagerActionView(pagerState)
    }
}