package com.optiflowx.optikeysx.views.keyboards.turkish

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
import com.optiflowx.optikeysx.views.keyboards.standard.StandardConstraintSets
import com.optiflowx.optikeysx.views.keyboards.standard.StandardRowKeys

@Composable
fun StandardKeyboardView(
    viewModel: KeyboardViewModel,
    viewWidth: Dp,
    keyHeight: Dp,
    rowHeight: Dp,
) {
    val locale = viewModel.keyboardData.collectAsState().value.locale
    val keyboardLocale = KeyboardLocale(locale)
    val constraintSets = StandardConstraintSets(keyHeight, rowHeight)
    val nRowKeys = StandardRowKeys()


    ConstraintLayout(
        constraintSet = constraintSets.constraints,
        modifier = Modifier.width(viewWidth),
        optimizationLevel = OPTIMIZATION_STANDARDIZED,
        animateChanges = true,
        
    ) {
        Box(modifier = Modifier.layoutId('1')) {
            ConstraintLayout(
                constraintSet = constraintSets.firstRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .padding(start = 2.dp, end = 3.5.dp)
                    .align(Alignment.Center),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                
            ) {
                for (key in nRowKeys.row1Keys) KeyboardKey(key, viewModel)
            }
        }
        Box(modifier = Modifier.layoutId('2')) {
            ConstraintLayout(
                constraintSet = constraintSets.secondRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .padding(start = 2.dp, end = 3.5.dp)
                    .align(Alignment.Center),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                
            ) {
                for (key in nRowKeys.row2Keys) KeyboardKey(key, viewModel)
            }
        }
        Box(modifier =  Modifier.layoutId('3')) {
            ConstraintLayout(
                constraintSet = constraintSets.thirdRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .padding(start = 2.dp, end = 3.5.dp)
                    .align(Alignment.Center),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                
            ) {
                for (key in nRowKeys.row3Keys) KeyboardKey(key, viewModel)
            }
        }
        Box(modifier = Modifier.layoutId('4')) {
            ConstraintLayout(
                constraintSet = constraintSets.fourthRowConstraints,
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
