package com.optiflowx.optikeysx.views.emoji

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.enums.KeyboardType
import com.optiflowx.optikeysx.core.model.Key
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import io.github.alexzhirkevich.cupertino.CupertinoSurface
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)

@Composable
fun EmojiPagerActionView(pagerState: PagerState, viewModel: KeyboardViewModel) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val iS = remember { MutableInteractionSource() }

    val listProps: ArrayList<Int> = arrayListOf(
        R.drawable.recents,
        R.drawable.smileys_people,
        R.drawable.animals_nature,
        R.drawable.food_drink,
        R.drawable.activity,
        R.drawable.travel_places,
        R.drawable.objects,
        R.drawable.symbols,
        R.drawable.flags
    )

    CupertinoSurface(
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
            Icon(
                painter = painterResource(R.drawable.abc),
                contentDescription = null,
                tint = CupertinoColors.systemGray(isSystemInDarkTheme()),
                modifier = Modifier
                    .padding(3.dp)
                    .size(25.dp)
                    .clickable(
                        indication = null,
                        interactionSource = iS,
                        role = Role.Button,
                    ) { viewModel.onUpdateKeyboardType(KeyboardType.Normal) },
            )
            for (i in listProps.indices) {
                val id = listProps[i]
                Icon(
                    painter = painterResource(id),
                    contentDescription = null,
                    tint = CupertinoColors.systemGray(isSystemInDarkTheme()),
                    modifier = Modifier
                        .background(
                            if (pagerState.currentPage == i) {
                                MaterialTheme.colorScheme.secondaryContainer
                            } else Color.Transparent,
                            RoundedCornerShape(20.dp),
                        )
                        .padding(6.dp)
                        .size(16.dp)
                        .clickable(
                            indication = null,
                            interactionSource = iS,
                            role = Role.Button,
                        ) { scope.launch { pagerState.scrollToPage(i) } },
                )
            }

            Icon(
                painter = painterResource(R.drawable.deletebackward),
                contentDescription = null,
                tint = CupertinoColors.systemGray(isSystemInDarkTheme()),
                modifier = Modifier
                    .padding(3.dp)
                    .size(18.dp)
                    .clickable(
                        indication = null,
                        interactionSource = iS,
                        role = Role.Button,
                    ) {
                        val key = Key("delete", "delete")
                        viewModel.onIKeyClick(key, context)
                        viewModel.playSound(key)
                        viewModel.vibrate()
                    },
            )
        }
    }
}