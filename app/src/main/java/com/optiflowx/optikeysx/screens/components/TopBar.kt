package com.optiflowx.optikeysx.screens.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun TopBar(
    lazyListState: LazyListState,
    title: String,
    actions: @Composable() (RowScope.() -> Unit) = {},
) {
    val density = LocalDensity.current

    val isTransparent by remember(lazyListState, density) {
        derivedStateOf {
            val isFirst = lazyListState.firstVisibleItemIndex < 2

            val isSecondWithPadding = lazyListState.firstVisibleItemIndex == 2 &&
                    lazyListState.firstVisibleItemScrollOffset < density.run { 20.dp.toPx() }

            isFirst || isSecondWithPadding
        }
    }

    CupertinoTopAppBar(
        // Currently UIKitView doesn't work inside a container with translucent app bars
        isTranslucent = isTransparent,
        isTransparent = isTransparent,
        actions = actions,
        title = {
            CupertinoText(title)
        }
    )
}