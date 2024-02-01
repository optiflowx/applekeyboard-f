package com.optiflowx.applekeyboard.views.number

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun NumberKeyboardView(
    viewModel: KeyboardViewModel, viewWidth: Dp, rowHeight: Int = 55,
    bottomDivHeight: Int = 30,
) {
    val numConst = NumberConstraintsSet(rowHeight, bottomDivHeight)
    val numRowKeys = NumberRowKeys()

    ConstraintLayout(numConst.constraints, Modifier.width(viewWidth), 100, true) {

        Box(Modifier.layoutId('1')) {
            ConstraintLayout(
                numConst.firstRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp), 100, true
            ) { for (key in numRowKeys.row1Keys) NumKeyboardKey(key, viewModel) }
        }
        Box(Modifier.layoutId('2')) {
            ConstraintLayout(
                numConst.secondRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp), 100, true
            ) { for (key in numRowKeys.row2Keys) NumKeyboardKey(key, viewModel) }
        }
        Box(Modifier.layoutId('3')) {
            ConstraintLayout(
                numConst.thirdRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp), 100, true
            ) { for (key in numRowKeys.row3Keys) NumKeyboardKey(key, viewModel) }
        }
        Box(Modifier.layoutId('4')) {
            ConstraintLayout(
                numConst.fourthRowConstraints,
                modifier = Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp),
                100, true
            ) { for (key in numRowKeys.row4Keys) NumKeyboardKey(key, viewModel) }
        }
    }
}