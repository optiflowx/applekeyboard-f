package com.optiflowx.optikeysx.views.normal

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.data.Key
import com.optiflowx.optikeysx.core.preferences.PrefsConstants
import com.optiflowx.optikeysx.core.preferences.rememberPreference
import com.optiflowx.optikeysx.core.utils.KeyboardLocale
import com.optiflowx.optikeysx.core.utils.OPTIMIZATION_STANDARDIZED
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import com.optiflowx.optikeysx.views.normal.constraintsets.GlobalConstraintSets
import com.optiflowx.optikeysx.views.normal.rowkeys.GlobalRowKeys

@Composable
fun NormalKeyboardView(
    viewModel: KeyboardViewModel,
    viewWidth: Dp,
    keyHeight: Dp = 44.dp,
    rowHeight: Dp = 56.dp,
) {
    val keyboardLocale = KeyboardLocale()
    val constraintSets = GlobalConstraintSets(keyHeight, rowHeight)
    val nRowKeys = GlobalRowKeys()
    val locale by rememberPreference(PrefsConstants.LOCALE_KEY, "English")

    ConstraintLayout(
        constraintSet = constraintSets.constraints,
        modifier = Modifier.width(viewWidth),
        optimizationLevel = OPTIMIZATION_STANDARDIZED,
        animateChanges = true,
        animationSpec = tween(350),
    ) {
        Box(modifier = Modifier.layoutId('1')) {
            ConstraintLayout(
                constraintSet = constraintSets.firstRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                animationSpec = tween(350),
            ) {
                for (key in nRowKeys.row1Keys) KeyboardKey(key, viewModel)
            }
        }
        Box(modifier = Modifier.layoutId('2')) {
            ConstraintLayout(
                constraintSet = constraintSets.secondRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                animationSpec = tween(350),
            ) {
                for (key in nRowKeys.row2Keys) KeyboardKey(key, viewModel)
            }
        }
        Box(modifier =  Modifier.layoutId('3')) {
            ConstraintLayout(
                constraintSet = constraintSets.thirdRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                animationSpec = tween(350),
            ) {
                for (key in nRowKeys.row3Keys) KeyboardKey(key, viewModel)
            }
        }
        Box(modifier = Modifier.layoutId('4')) {
            ConstraintLayout(
                constraintSet = constraintSets.fourthRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                animationSpec = tween(350),
            ) {
                KeyboardKey(Key("123", stringResource(R.string.num)), viewModel)
                KeyboardKey(Key("emoji", "emoji"), viewModel)
                KeyboardKey(Key("space", keyboardLocale.space(locale)), viewModel)
                KeyboardKey(
                    Key("action", keyboardLocale.action("return", locale)),
                    viewModel
                )
            }
        }
    }
}
