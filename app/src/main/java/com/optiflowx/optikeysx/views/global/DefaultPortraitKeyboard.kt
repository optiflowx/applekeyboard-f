package com.optiflowx.optikeysx.views.global

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.optiflowx.optikeysx.core.enums.KeyboardType
import com.optiflowx.optikeysx.core.utils.OPTIMIZATION_STANDARDIZED
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import com.optiflowx.optikeysx.views.clipboard.ClipboardKeyboardView
import com.optiflowx.optikeysx.views.emoji.EmojiKeyboardView
import com.optiflowx.optikeysx.views.normal.NormalKeyboardView
import com.optiflowx.optikeysx.views.symbols.SymbolsKeyboardView

@Composable
fun PortraitKeyboard(
    viewModel: KeyboardViewModel
) {
    val keyboardType = viewModel.keyboardType.collectAsState()

    val viewWidth = LocalConfiguration.current.screenWidthDp.dp

    val constraintsSet = ConstraintSet {
        val topView = createRefFor("topView")
        val keyboardView = createRefFor("keyboardView")
        val bottomView = createRefFor("bottomView")

        constrain(topView) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(keyboardView) {
            top.linkTo(topView.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(bottomView) {
            top.linkTo(keyboardView.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
    }

    ConstraintLayout(
        constraintSet = constraintsSet,
        modifier = Modifier.mandatorySystemGesturesPadding()
            .wrapContentSize(),
        optimizationLevel = OPTIMIZATION_STANDARDIZED,
        animateChanges = true,
        animationSpec = tween(350),
    ) {
        KeyboardTopView(
            viewModel = viewModel,
            viewWidth = viewWidth
        )

        Box(modifier = Modifier.layoutId("keyboardView")) {
            when (keyboardType.value) {
                KeyboardType.Normal -> NormalKeyboardView(viewModel, viewWidth)

                KeyboardType.Symbol -> SymbolsKeyboardView(viewModel, viewWidth)

                KeyboardType.Emoji -> EmojiKeyboardView(viewModel, viewWidth)

                KeyboardType.Clipboard -> ClipboardKeyboardView(viewModel, viewWidth)
            }
        }

        KeyboardBottomView(viewModel)
    }
}