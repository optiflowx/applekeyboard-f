package com.optiflowx.optikeysx.views.keyboards.portuguese

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.model.Key
import com.optiflowx.optikeysx.core.utils.KeyboardLocale
import com.optiflowx.optikeysx.core.utils.OPTIMIZATION_STANDARDIZED
import com.optiflowx.optikeysx.ui.keyboard.KeyboardKey
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel

@Composable
fun PortugueseKeyboardView(
    viewModel: KeyboardViewModel,
    viewWidth: Dp,
    keyHeight: Dp = 42.dp,
    rowHeight: Dp = 56.dp,
) {
    val locale = viewModel.locale.collectAsState().value
    val keyboardLocale = KeyboardLocale(locale)
    val ptRowKeys = PortugueseARowKeys()
    val ptBrRowKeys = PortugueseBRowKeys()

    ConstraintLayout(
        constraintSet = if (locale == "pt-PT") {
            PortugueseAConstraintSets(keyHeight, rowHeight).constraints
        } else PortugueseBConstraintSets(keyHeight, rowHeight).constraints,
        modifier = Modifier.width(viewWidth),
        optimizationLevel = OPTIMIZATION_STANDARDIZED,
        animateChanges = true,
        
    ) {
        Box(modifier = Modifier.layoutId('1')) {
            ConstraintLayout(
                constraintSet = if (locale == "pt-PT") {
                    PortugueseAConstraintSets(keyHeight, rowHeight).firstRowConstraints
                } else PortugueseBConstraintSets(keyHeight, rowHeight).firstRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .padding(start = 2.dp, end = 3.5.dp)
                    .align(Alignment.Center),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                
            ) {
                if (locale == "pt-PT") {
                    for (key in ptRowKeys.row1Keys) KeyboardKey(key, viewModel)
                } else {
                    for (key in ptBrRowKeys.row1Keys) KeyboardKey(key, viewModel)
                }

            }
        }
        Box(modifier = Modifier.layoutId('2')) {
            ConstraintLayout(
                constraintSet = if (locale == "pt-PT") {
                    PortugueseAConstraintSets(keyHeight, rowHeight).secondRowConstraints
                } else PortugueseBConstraintSets(keyHeight, rowHeight).secondRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .padding(start = 2.dp, end = 3.5.dp)
                    .align(Alignment.Center),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                
            ) {
                if (locale == "pt-PT") {
                    for (key in ptRowKeys.row2Keys) KeyboardKey(key, viewModel)
                } else {
                    for (key in ptBrRowKeys.row2Keys) KeyboardKey(key, viewModel)
                }
            }
        }
        Box(modifier =  Modifier.layoutId('3')) {
            ConstraintLayout(
                constraintSet = if (locale == "pt-PT") {
                    PortugueseAConstraintSets(keyHeight, rowHeight).thirdRowConstraints
                } else PortugueseBConstraintSets(keyHeight, rowHeight).thirdRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .padding(start = 2.dp, end = 3.5.dp)
                    .align(Alignment.Center),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                
            ) {
                if (locale == "pt-PT") {
                    for (key in ptRowKeys.row3Keys) KeyboardKey(key, viewModel)
                } else {
                    for (key in ptBrRowKeys.row3Keys) KeyboardKey(key, viewModel)
                }
            }
        }
        Box(modifier = Modifier.layoutId('4')) {
            ConstraintLayout(
                constraintSet = if (locale == "pt-PT") {
                    PortugueseAConstraintSets(keyHeight, rowHeight).fourthRowConstraints
                } else PortugueseBConstraintSets(keyHeight, rowHeight).fourthRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .padding(start = 2.dp, end = 3.5.dp)
                    .align(Alignment.Center),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
            ) {
                KeyboardKey(Key("123", stringResource(R.string.num)), viewModel)
                KeyboardKey(Key("emoji", "emoji"), viewModel)
                KeyboardKey(Key("space", keyboardLocale.space()), viewModel)
                KeyboardKey(
                    Key("action", keyboardLocale.action("return")),
                    viewModel
                )
            }
        }
    }
}
