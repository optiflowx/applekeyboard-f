package com.optiflowx.applekeyboard.views.phone

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.optiflowx.applekeyboard.core.preferences.PreferencesConstants
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun PhoneNumberKeyboardView(
    viewModel: KeyboardViewModel, viewWidth: Dp, rowHeight: Int = 55,
    bottomDivHeight: Int = 30,
) {
    val isPhoneSymbols = viewModel.isPhoneSymbol.observeAsState().value!!
    val locale = viewModel.preferences.getFlowPreference(
        PreferencesConstants.LOCALE_KEY, "English"
    ).collectAsStateWithLifecycle("English").value
    val phoneRowKeys = PhoneRowKeys(locale)
    val phoneConst = PhoneConstraintsSet(rowHeight, bottomDivHeight)

    ConstraintLayout(phoneConst.constraints, Modifier.width(viewWidth), 100, true) {

        Box(Modifier.layoutId('1')) {
            ConstraintLayout(
                phoneConst.firstRowConstraints,
                Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp), 100, true
            ) { for (key in phoneRowKeys.row1Keys) PhoneNumKeyboardKey(key, viewModel) }
        }
        Box(Modifier.layoutId('2')) {
            ConstraintLayout(
                phoneConst.secondRowConstraints,
                Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp), 100, true
            ) {
                if (isPhoneSymbols) for (key in phoneRowKeys.row2KeysB) PhoneNumKeyboardKey(
                    key,
                    viewModel
                ) else for (key in phoneRowKeys.row2Keys) PhoneNumKeyboardKey(
                    key,
                    viewModel
                )
            }
        }
        Box(Modifier.layoutId('3')) {
            ConstraintLayout(
                phoneConst.thirdRowConstraints,
                Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp), 100, true
            ) {
                if (isPhoneSymbols) for (key in phoneRowKeys.row3KeysB) PhoneNumKeyboardKey(
                    key,
                    viewModel
                ) else for (key in phoneRowKeys.row3Keys) PhoneNumKeyboardKey(
                    key,
                    viewModel
                )
            }
        }
        Box(Modifier.layoutId('4')) {
            ConstraintLayout(
                phoneConst.fourthRowConstraints,
                Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp), 100, true
            ) {
                if (isPhoneSymbols) for (key in phoneRowKeys.row4keysB) PhoneNumKeyboardKey(
                    key,
                    viewModel
                ) else for (key in phoneRowKeys.row4Keys) PhoneNumKeyboardKey(
                    key,
                    viewModel
                )
            }
        }
    }
}