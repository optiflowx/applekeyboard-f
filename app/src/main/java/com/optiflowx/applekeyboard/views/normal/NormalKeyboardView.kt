package com.optiflowx.applekeyboard.views.normal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
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
import com.optiflowx.applekeyboard.views.normal.constraintsets.GlobalConstraintSets
import com.optiflowx.applekeyboard.views.normal.rowkeys.GlobalRowKeys

@Composable
fun NormalKeyboardView(viewModel: KeyboardViewModel) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val keyboardLocale = KeyboardLocale()
    val constraintSets = GlobalConstraintSets()
    val nRowKeys = GlobalRowKeys()
    val locale = viewModel.preferences.getFlowPreference(PreferencesConstants.LOCALE_KEY, "English")
        .collectAsStateWithLifecycle("English").value

    ConstraintLayout(constraintSets.constraints, Modifier.width(screenWidth),
        100, true) {
        val keyWidth = (screenWidth.value * 0.082).dp
        val keyWidthB = (screenWidth.value * 0.45).dp
        val keyWidthM = (screenWidth.value * 0.25).dp
        val keyWidthSE = (screenWidth.value * 0.11).dp

        Box(Modifier.layoutId('1')) {
            ConstraintLayout(
                constraintSets.firstRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp), 100, true
            ) {
                for (key in nRowKeys.row1Keys) KeyboardKey(key, keyWidth, viewModel)
            }
        }
        Box(Modifier.layoutId('2')) {
            ConstraintLayout(
                constraintSets.secondRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(
                        horizontal = when (locale) {
                            "Spanish" -> 4.dp
                            else -> 20.dp
                        }
                    ),100, true
            ) {

                for (key in nRowKeys.row2Keys) KeyboardKey(key, keyWidth, viewModel)

            }
        }
        Box(Modifier.layoutId('3')) {
            ConstraintLayout(
                constraintSets.thirdRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                100, true
            ) {

                for (key in nRowKeys.row3Keys) {
                    if (key.id == "shift" || key.id == "erase") {
                        KeyboardKey(key, keyWidthSE, viewModel)
                    } else KeyboardKey(key, keyWidth, viewModel)
                }

            }
        }
        Box(Modifier.layoutId('4')) {
            ConstraintLayout(
                constraintSets.fourthRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),100, true
            ) {
                KeyboardKey(Key("123", stringResource(R.string.num)), keyWidthSE, viewModel)
                KeyboardKey(Key("emoji", "emoji"), keyWidthSE, viewModel)
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
