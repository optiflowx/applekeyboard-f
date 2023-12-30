package com.optiflowx.applekeyboard.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.models.Key
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import io.github.alexzhirkevich.cupertino.Surface
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)

@Composable
fun EmojiPagerActionView(pagerState: PagerState, viewModel: KeyboardViewModel) {
    val listProps: ArrayList<Int> = arrayListOf(
        R.drawable.recents,
        R.drawable.smileys_people,
        R.drawable.animals_nature,
        R.drawable.food_drink,
        R.drawable.activity,
        R.drawable.travel_places,
        R.drawable.objects,
        R.drawable.symbols,
        R.drawable.flags,
        R.drawable.ic_backspace
    )

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            for (i in listProps.indices) {
                val id = listProps[i]
                Image(
                    painter = painterResource(id),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(CupertinoColors.systemGray(isSystemInDarkTheme())),
                    modifier = Modifier
                        .background(
                            (if (id != R.drawable.ic_backspace) {
                                if (pagerState.currentPage == i) {
                                    MaterialTheme.colorScheme.secondaryContainer
                                } else Color.Transparent
                            } else Color.Transparent),
                            RoundedCornerShape(20.dp),
                        )
                        .padding(5.dp)
                        .size(15.dp)
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource,
                            onClick = {
                                if (id != R.drawable.ic_backspace) {
                                    scope.launch { pagerState.scrollToPage(i) }
                                } else {
                                    viewModel.onIKeyClick(
                                        Key("erase", "erase"),
                                        context,
                                        viewModel.soundPool?.load(context, R.raw.delete, 1),
                                    )
                                }
                            },
                        ),
                )
            }
        }
    }
}