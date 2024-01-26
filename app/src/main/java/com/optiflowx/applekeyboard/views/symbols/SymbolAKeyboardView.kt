package com.optiflowx.applekeyboard.views.symbols

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.core.data.Key
import com.optiflowx.applekeyboard.core.preferences.PreferencesConstants
import com.optiflowx.applekeyboard.core.utils.KeyboardLocale
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.normal.KeyboardKey

@Composable
fun SymbolAKeyboardView(viewModel: KeyboardViewModel) {
    val locale = viewModel.preferences.getFlowPreference(
        PreferencesConstants.LOCALE_KEY, "English"
    ).collectAsStateWithLifecycle("English").value

    val isSymbol = viewModel.isNumberSymbol.observeAsState().value
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val symbolConst = SymbolConstraintSet()
    val keyboardLocale = KeyboardLocale()
    val symbolRowKeys = SymbolRowKeys()

    ConstraintLayout(symbolConst.main, Modifier.width(screenWidth)) {
        val keyWidth = (screenWidth.value * 0.082).dp
        val keyWidthR3 = (screenWidth.value * 0.11).dp
        val keyWidthB = (screenWidth.value * 0.45).dp
        val keyWidthM = (screenWidth.value * 0.25).dp
        val keyWidthSE = (screenWidth.value * 0.13).dp
        val keyWidthSEB = (screenWidth.value * 0.11).dp

        Box(Modifier.layoutId('1')) {
            ConstraintLayout(
                symbolConst.firstRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp), 100, true
            ) {
                if (isSymbol != true) {
                    for (key in symbolRowKeys.row1Keys) KeyboardKey(key, keyWidth, viewModel)
                } else {
                    for (key in symbolRowKeys.row1SymbolKeys) KeyboardKey(key, keyWidth, viewModel)
                }
            }
        }
        Box(Modifier.layoutId('2')) {
            ConstraintLayout(
                symbolConst.secondRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp), 100, true
            ) {
                if (isSymbol != true) {
                    for (key in symbolRowKeys.row2Keys) KeyboardKey(key, keyWidth, viewModel)
                } else {
                    for (key in symbolRowKeys.row2SymbolKeys) KeyboardKey(key, keyWidth, viewModel)
                }
            }
        }
        Box(Modifier.layoutId('3')) {
            ConstraintLayout(
                symbolConst.thirdRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                100, true
            ) {
                KeyboardKey(Key("symbol", ""), keyWidthSE, viewModel)
                for (key in symbolRowKeys.row3Keys) KeyboardKey(key, keyWidthR3, viewModel)
                KeyboardKey(Key("erase", ""), keyWidthSE, viewModel)
            }
        }
        Box(Modifier.layoutId('4')) {
            ConstraintLayout(
                symbolConst.fourthRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp), 100, true
            ) {
                KeyboardKey(Key("ABC", stringResource(R.string.abc)), keyWidthSEB, viewModel)
                KeyboardKey(Key("emoji", "emoji"), keyWidthSEB, viewModel)
                KeyboardKey(Key("space", keyboardLocale.space(locale)), keyWidthB, viewModel)
                KeyboardKey(
                    Key("action", keyboardLocale.action("return", locale)),
                    keyWidthM,
                    viewModel
                )
            }
        }
    }
}