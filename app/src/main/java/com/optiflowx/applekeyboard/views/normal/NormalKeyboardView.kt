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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
fun NormalKeyboardView(
    viewModel: KeyboardViewModel,
    viewWidth: Dp,
    keyHeight: Dp = 44.dp,
    rowHeight: Dp = 56.dp,
) {
    val keyboardLocale = KeyboardLocale()
    val constraintSets = GlobalConstraintSets(keyHeight, rowHeight)
    val nRowKeys = GlobalRowKeys()
    val locale = viewModel.preferences.getFlowPreference(PreferencesConstants.LOCALE_KEY, "English")
        .collectAsStateWithLifecycle("English").value

    ConstraintLayout(
        constraintSets.constraints, Modifier.width(viewWidth),
        100, true
    ) {
        Box(Modifier.layoutId('1')) {
            ConstraintLayout(
                constraintSets.firstRowConstraints,
                Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp), 100, true
            ) {
                for (key in nRowKeys.row1Keys) KeyboardKey(key, viewModel)
            }
        }
        Box(Modifier.layoutId('2')) {
            ConstraintLayout(
                constraintSets.secondRowConstraints,
                Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp), 100, true
            ) {
                for (key in nRowKeys.row2Keys) KeyboardKey(key, viewModel)
            }
        }
        Box(Modifier.layoutId('3')) {
            ConstraintLayout(
                constraintSets.thirdRowConstraints,
                Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp),
                100, true
            ) {
                for (key in nRowKeys.row3Keys) {
                    if (key.id == "shift" || key.id == "delete") {
                        KeyboardKey(key, viewModel)
                    } else KeyboardKey(key, viewModel)
                }
            }
        }
        Box(Modifier.layoutId('4')) {
            ConstraintLayout(
                constraintSets.fourthRowConstraints,
                Modifier
                    .width(viewWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 4.dp), 100, true
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
