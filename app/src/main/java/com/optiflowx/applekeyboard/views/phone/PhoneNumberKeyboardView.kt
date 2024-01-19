package com.optiflowx.applekeyboard.views.phone

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.optiflowx.applekeyboard.storage.PreferencesConstants
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun PhoneNumberKeyboardView(viewModel: KeyboardViewModel) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val isPhoneSymbols = viewModel.isPhoneSymbol.observeAsState().value!!

    var locale by rememberSaveable { mutableStateOf("ENGLISH") }

    LaunchedEffect(viewModel.preferences) {
        locale = viewModel.preferences.getStaticPreference(PreferencesConstants.LOCALE_KEY, "ENGLISH")
    }

    val phoneRowKeys = PhoneRowKeys(locale)
    val phoneConst = PhoneConstraintsSet()

    ConstraintLayout(phoneConst.constraints, Modifier.width(screenWidth), 100, true) {
        val keyWidth = (screenWidth.value * 0.31).dp

        Box(Modifier.layoutId('1')) {
            ConstraintLayout(
                phoneConst.firstRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp), 100, true
            ) { for (key in phoneRowKeys.row1Keys) PhoneNumKeyboardKey(key, keyWidth, viewModel) }
        }
        Box(Modifier.layoutId('2')) {
            ConstraintLayout(
                phoneConst.secondRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp), 100, true
            ) {
                if (isPhoneSymbols) {
                    for (key in phoneRowKeys.row2KeysB) PhoneNumKeyboardKey(key, keyWidth, viewModel)
                } else for (key in phoneRowKeys.row2Keys) PhoneNumKeyboardKey(key, keyWidth, viewModel)
            }
        }
        Box(Modifier.layoutId('3')) {
            ConstraintLayout(
                phoneConst.thirdRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 2.dp),
                100, true
            ) {
                if (isPhoneSymbols) {
                    for (key in phoneRowKeys.row3KeysB) PhoneNumKeyboardKey(key, keyWidth, viewModel)
                } else for (key in phoneRowKeys.row3Keys) PhoneNumKeyboardKey(key, keyWidth, viewModel)
            }
        }
        Box(Modifier.layoutId('4')) {
            ConstraintLayout(
                phoneConst.fourthRowConstraints,
                Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 2.dp), 100, true
            ) {
                if (isPhoneSymbols) {
                    for (key in phoneRowKeys.row4keysB) PhoneNumKeyboardKey(key, keyWidth, viewModel)
                } else for (key in phoneRowKeys.row4Keys) PhoneNumKeyboardKey(key, keyWidth, viewModel)
            }
        }
    }
}