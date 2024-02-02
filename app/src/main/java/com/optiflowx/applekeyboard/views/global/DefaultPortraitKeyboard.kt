package com.optiflowx.applekeyboard.views.global

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
import com.optiflowx.applekeyboard.core.enums.KeyboardType
import com.optiflowx.applekeyboard.core.utils.OPTIMIZATION_STANDARDIZED
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.clipboard.ClipboardKeyboardView
import com.optiflowx.applekeyboard.views.emoji.EmojiKeyboardView
import com.optiflowx.applekeyboard.views.normal.NormalKeyboardView
import com.optiflowx.applekeyboard.views.symbols.SymbolsKeyboardView

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
        modifier = Modifier
            .wrapContentSize()
            .mandatorySystemGesturesPadding(),
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