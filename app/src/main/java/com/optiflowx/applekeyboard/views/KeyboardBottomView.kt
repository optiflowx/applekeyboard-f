package com.optiflowx.applekeyboard.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.ui.defaultFontFamily
import com.optiflowx.applekeyboard.utils.KeyboardType
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Mic
import kotlinx.coroutines.launch

@Composable
fun KeyboardBottomView(viewM: KeyboardViewModel) {
    val keyboardType = viewM.keyboardType.observeAsState()
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val scope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(50.dp)
            .fillMaxHeight()
            .padding(horizontal = 35.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            if (keyboardType.value == KeyboardType.Emoji) {
                Text(
                    text = "ABC",
                    color = MaterialTheme.colors.primary,
                    fontFamily = defaultFontFamily,
                    fontSize = TextUnit(18f, TextUnitType.Sp),
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = interactionSource,
                        onClick = {
                            scope.launch {
                                viewM.keyboardSize.value = IntOffset(
                                    viewM.keyboardSize.value?.x!!,
                                    viewM.keyboardSize.value?.y!! - 30
                                )
                                viewM.keyboardType.value = KeyboardType.Normal
                            }
                        }
                    ),
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.emoji),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    modifier = Modifier
                        .height(30.dp)
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource,
                            onClick = {
                                scope.launch {
                                    viewM.keyboardSize.value = IntOffset(
                                        viewM.keyboardSize.value?.x!!,
                                        viewM.keyboardSize.value?.y!! + 30
                                    )
                                    viewM.keyboardType.value = KeyboardType.Emoji
                                }
                            }
                        ),
                )
            }

            Icon(
                imageVector = CupertinoIcons.Outlined.Mic,
                contentDescription = "mic",
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colors.primary,
            )
        }
    }
}