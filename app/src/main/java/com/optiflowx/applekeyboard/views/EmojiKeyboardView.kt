package com.optiflowx.applekeyboard.views

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.optiflowx.applekeyboard.composables.EmojiItem
import com.optiflowx.applekeyboard.database.EmojisDatabase
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.ui.mediumFontFamily
import com.optiflowx.applekeyboard.utils.emojiData
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray

@Preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmojiKeyboardView() {
    val colors = MaterialTheme.colors
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val context = LocalContext.current

    val viewModel = viewModel<KeyboardViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return KeyboardViewModel(screenWidth, colors) as T
            }
        }
    )

    if(viewModel.dataB == null) {
        viewModel.dataB = Room.inMemoryDatabaseBuilder(context, EmojisDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        viewModel.emojiDataDao = viewModel.dataB?.appDataDAO()
    }

    val frequentEmojis = viewModel.emojiDataDao?.getAllEmojis()?.observeAsState()?.value?.reversed()

    val emojiViewPager = arrayListOf(
        hashMapOf("Frequently Used" to listOf()),
        hashMapOf("Smileys & People" to emojiData.smilesAndPeople),
        hashMapOf("Animals & Nature" to emojiData.animalsAndNature),
        hashMapOf("Food & Drink" to emojiData.foodAndDrinks),
        hashMapOf("Activities" to emojiData.activities),
        hashMapOf("Travel & Places" to emojiData.travelAndPlaces),
        hashMapOf("Objects" to emojiData.objects),
        hashMapOf("Symbols" to emojiData.symbols),
        hashMapOf("Flags" to emojiData.flags),
    )

    val pagerState = rememberPagerState(pageCount = { emojiViewPager.size }, initialPage = 0)
    val freqViewPort = (screenWidth * 0.76f).dp
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