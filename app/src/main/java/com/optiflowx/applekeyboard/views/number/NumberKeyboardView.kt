package com.optiflowx.applekeyboard.views.number

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun NumberKeyboardView(viewModel: KeyboardViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val numConst = NumberConstraintsSet()
    val numRowKeys = NumberRowKeys()

    ConstraintLayout(numConst.constraints, Modifier.width(screenWidth),100, true) {
        val keyWidth = (screenWidth.value * 0.31).dp

        Box(Modifier.layoutId('1')) {
            ConstraintLayout(
                numConst.firstRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp), 100, true
            ) { for (key in numRowKeys.row1Keys) NumKeyboardKey(key, keyWidth, viewModel) }
        }
        Box(Modifier.layoutId('2')) {
            ConstraintLayout(
                numConst.secondRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp), 100, true
            ) { for (key in numRowKeys.row2Keys) NumKeyboardKey(key, keyWidth, viewModel) }
        }
        Box(Modifier.layoutId('3')) {
            ConstraintLayout(
                numConst.thirdRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 2.dp), 100, true
            ) {
                for (key in numRowKeys.row3Keys) {
                    if (key.id == "." || key.id == "delete") {
                        NumKeyboardKey(key, keyWidth, viewModel)
                    } else NumKeyboardKey(key, keyWidth, viewModel)
                }
            }
        }
        Box(Modifier.layoutId('4')) {
            ConstraintLayout(
                numConst.fourthRowConstraints,
                Modifier.align(Alignment.Center).padding(horizontal = 2.dp),
                100, true
            ) { for(key in numRowKeys.row4Keys) NumKeyboardKey(key, keyWidth, viewModel) }
        }
    }
}