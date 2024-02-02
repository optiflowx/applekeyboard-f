package com.optiflowx.applekeyboard.views.symbols

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.core.data.Key
import com.optiflowx.applekeyboard.core.preferences.PrefsConstants
import com.optiflowx.applekeyboard.core.preferences.rememberPreference
import com.optiflowx.applekeyboard.core.utils.KeyboardLocale
import com.optiflowx.applekeyboard.core.utils.OPTIMIZATION_STANDARDIZED
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.normal.KeyboardKey

@Composable
fun SymbolsKeyboardView(
    viewModel: KeyboardViewModel,
    viewWidth: Dp,
    keyHeight: Dp = 44.dp,
    rowHeight: Dp = 56.dp,
) {
    val locale by rememberPreference(PrefsConstants.LOCALE_KEY, "English")
    val isSymbol = viewModel.isNumberSymbol.collectAsState().value
    val symbolConst = SymbolConstraintSet(keyHeight, rowHeight)
    val keyboardLocale = KeyboardLocale()
    val symbolRowKeys = SymbolRowKeys()

    ConstraintLayout(
        constraintSet = symbolConst.main,
        modifier = Modifier.width(viewWidth),
        optimizationLevel = OPTIMIZATION_STANDARDIZED,
        animateChanges = true,
        animationSpec = tween(350),
    ) {
        Box(Modifier.layoutId('1')) {
            ConstraintLayout(
                constraintSet = symbolConst.firstRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                animationSpec = tween(350),
            ) {
                if (!isSymbol) {
                    for (key in symbolRowKeys.row1Keys) KeyboardKey(key, viewModel)
                } else {
                    for (key in symbolRowKeys.row1SymbolKeys) KeyboardKey(key, viewModel)
                }
            }
        }
        Box(Modifier.layoutId('2')) {
            ConstraintLayout(
                constraintSet = symbolConst.secondRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                animationSpec = tween(350),
            ) {
                if (!isSymbol) {
                    for (key in symbolRowKeys.row2Keys) KeyboardKey(key, viewModel)
                } else {
                    for (key in symbolRowKeys.row2SymbolKeys) KeyboardKey(key, viewModel)
                }
            }
        }
        Box(Modifier.layoutId('3')) {
            ConstraintLayout(
                constraintSet = symbolConst.thirdRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                optimizationLevel = OPTIMIZATION_STANDARDIZED, animateChanges = true,
                animationSpec = tween(350),
            ) {
                KeyboardKey(Key("symbol", ""), viewModel)
                for (key in symbolRowKeys.row3Keys) KeyboardKey(key, viewModel)
                KeyboardKey(Key("delete", ""), viewModel)
            }
        }
        Box(Modifier.layoutId('4')) {
            ConstraintLayout(
                constraintSet = symbolConst.fourthRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                animationSpec = tween(350),
            ) {
                KeyboardKey(Key("ABC", stringResource(R.string.abc)), viewModel)
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