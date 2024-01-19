package com.optiflowx.applekeyboard.views.global

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.ui.regular
import com.optiflowx.applekeyboard.utils.KeyboardType
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Mic

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun KeyboardBottomView(viewModel: KeyboardViewModel) {
    val keyboardType = viewModel.keyboardType.observeAsState()
    val keyboardWidth = LocalConfiguration.current.screenWidthDp
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(horizontal = (keyboardWidth * 0.075).dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (keyboardType.value == KeyboardType.Emoji) {
                Text(
                    text = "ABC",
                    color = MaterialTheme.colorScheme.scrim,
                    fontFamily = regular,
                    fontSize = TextUnit(17f, TextUnitType.Sp),
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = interactionSource,
                        onClick = { viewModel.onABCTap() }
                    ),
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.emoji),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.scrim),
                    modifier = Modifier
                        .height(30.dp)
                        .combinedClickable(
                            indication = null,
                            interactionSource = interactionSource,
                            onClick = { viewModel.onEmojiTap() },
                            onLongClick = { }
                        ),
                )
            }

            Icon(
                imageVector = CupertinoIcons.Outlined.Mic,
                contentDescription = "mic",
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.scrim,
            )
        }
    }
}