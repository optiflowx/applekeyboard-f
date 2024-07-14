package com.optiflowx.optikeysx.ime.views.defaults

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.optiflowx.optikeysx.core.enums.KeyboardType
import com.optiflowx.optikeysx.core.utils.OPTIMIZATION_STANDARDIZED
import com.optiflowx.optikeysx.ime.components.KeyboardBottomView
import com.optiflowx.optikeysx.ime.components.KeyboardTopView
import com.optiflowx.optikeysx.ime.viewmodel.KeyboardViewModel
import com.optiflowx.optikeysx.ime.views.clipboard.ClipboardKeyboardView
import com.optiflowx.optikeysx.ime.views.emoji.EmojiKeyboardView
import com.optiflowx.optikeysx.ime.views.keyboards.french.FrenchKeyboardView
import com.optiflowx.optikeysx.ime.views.keyboards.portuguese.PortugueseKeyboardView
import com.optiflowx.optikeysx.ime.views.keyboards.russian.RussianKeyboardView
import com.optiflowx.optikeysx.ime.views.keyboards.spanish.SpanishKeyboardView
import com.optiflowx.optikeysx.ime.views.keyboards.standard.StandardKeyboardView
import com.optiflowx.optikeysx.ime.views.symbols.SymbolsKeyboardView

@Composable
fun DefaultPortraitKeyboard(vM: KeyboardViewModel) {
    val vW = LocalConfiguration.current.screenWidthDp.dp
    val keyboardType = vM.keyboardType.collectAsState()
    val locale = vM.keyboardData.collectAsState().value.locale

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
        modifier = Modifier.navigationBarsPadding(),
        optimizationLevel = OPTIMIZATION_STANDARDIZED,
        animateChanges = true,
    ) {
        KeyboardTopView(vM, vW)

        Box(
            modifier = Modifier
                .layoutId("keyboardView")
                .wrapContentSize(),
            contentAlignment = Alignment.Center,
        ) {
            when (keyboardType.value) {
                KeyboardType.Normal -> when (locale) {
                    "pt-BR" -> PortugueseKeyboardView(vM, vW)
                    "pt-PT" -> PortugueseKeyboardView(vM, vW)
                    "fr-FR" -> FrenchKeyboardView(vM, vW)
                    "es" -> SpanishKeyboardView(vM, vW)
                    "ru" -> RussianKeyboardView(vM, vW)
                    else -> StandardKeyboardView(vM, vW)
                }

                KeyboardType.Symbol -> SymbolsKeyboardView(vM, vW)

                KeyboardType.Emoji -> EmojiKeyboardView(vM, vW)

                KeyboardType.Clipboard -> ClipboardKeyboardView(vM, vW)

            }
        }

        KeyboardBottomView(vM)
    }
}