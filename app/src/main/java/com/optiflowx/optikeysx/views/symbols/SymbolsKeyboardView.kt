package com.optiflowx.optikeysx.views.symbols

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
fun SymbolsKeyboardView(
    viewModel: KeyboardViewModel,
    viewWidth: Dp,
    keyHeight: Dp = 42.dp,
    rowHeight: Dp = 56.dp,
) {
    
    val isSymbol = viewModel.isNumberSymbol.collectAsState().value
    val symbolConst = SymbolConstraintSet(keyHeight, rowHeight)
    val locale = viewModel.keyboardData.collectAsState().value.locale
    val keyboardLocale = KeyboardLocale(locale)
    val symbolRowKeys = SymbolRowKeys()

    ConstraintLayout(
        constraintSet = symbolConst.main,
        modifier = Modifier.width(viewWidth),
        optimizationLevel = OPTIMIZATION_STANDARDIZED,
        animateChanges = true,
        
    ) {
        Box(Modifier.layoutId('1')) {
            ConstraintLayout(
                constraintSet = symbolConst.firstRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .padding(start = 2.dp, end = 3.5.dp)
                    .align(Alignment.Center),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                
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
                    .padding(start = 2.dp, end = 3.5.dp)
                    .align(Alignment.Center),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                
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
                    .padding(start = 2.dp, end = 3.5.dp)
                    .align(Alignment.Center),
                optimizationLevel = OPTIMIZATION_STANDARDIZED, animateChanges = true,

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
                    .padding(start = 2.dp, end = 3.5.dp)
                    .align(Alignment.Center),
                optimizationLevel = OPTIMIZATION_STANDARDIZED,
                animateChanges = true,
                
            ) {
                KeyboardKey(Key("ABC", stringResource(R.string.abc)), viewModel)
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