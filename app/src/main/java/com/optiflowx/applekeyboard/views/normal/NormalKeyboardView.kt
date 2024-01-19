package com.optiflowx.applekeyboard.views.normal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.models.Key
import com.optiflowx.applekeyboard.storage.PreferencesConstants
import com.optiflowx.applekeyboard.utils.KeyboardLanguage
import com.optiflowx.applekeyboard.utils.KeyboardLocale
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun NormalKeyboardView(viewModel: KeyboardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val keyboardLocale = KeyboardLocale()
    val nConst = NormalConstraintSets()
    val nRowKeys = NormalRowKeys()
    var locale by rememberSaveable { mutableStateOf("ENGLISH") }

    LaunchedEffect(viewModel.preferences) {
        locale = viewModel.preferences.getLastPreference(PreferencesConstants.LOCALE_KEY, "ENGLISH")
    }

//    Popup(
//        offset = IntOffset(
//            x = 180, y = 200
//        )
//    ) {
//        Box(
//            Modifier.draw9Patch(LocalContext.current, R.drawable.popup_key_light)
//        ) {
//            Text(
//                text = "A",
//                Modifier.padding(10.dp).absoluteOffset(y = (-5).dp)
//            )
//        }
//    }

    ConstraintLayout(nConst.mainColumnConstraints, Modifier.width(screenWidth), 100, true) {
        val keyWidth = (screenWidth.value * 0.082).dp
        val keyWidthB = (screenWidth.value * 0.45).dp
        val keyWidthM = (screenWidth.value * 0.25).dp
        val keyWidthSE = (screenWidth.value * 0.11).dp

        Box(
            Modifier
                .layoutId('1')
        ) {
            ConstraintLayout(
                when (locale) {
                    KeyboardLanguage.FRENCH.name -> nConst.firstRowConstraintsFr
                    else -> nConst.firstRowConstraints
                },
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp), 100, true
            ) {
                when (locale) {
                    KeyboardLanguage.FRENCH.name -> {
                        for (key in nRowKeys.row1KeysFr) KeyboardKey(key, keyWidth, viewModel)
                    }

                    else -> {
                        for (key in nRowKeys.row1Keys) KeyboardKey(key, keyWidth, viewModel)
                    }
                }
            }
        }
        Box(
            Modifier
                .layoutId('2')
        ) {
            ConstraintLayout(
                when (locale) {
                    KeyboardLanguage.SPANISH.name -> nConst.secondRowConstraintsSP
                    KeyboardLanguage.FRENCH.name -> nConst.secondRowConstraintsFr
                    else -> nConst.secondRowConstraints
                },
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(
                        horizontal = when (locale) {
                            KeyboardLanguage.SPANISH.name -> 4.dp
                            else -> 20.dp
                        }
                    ), 100, true
            ) {
                when (locale) {
                    KeyboardLanguage.SPANISH.name -> {
                        for (key in nRowKeys.row2KeysSP) KeyboardKey(key, keyWidth, viewModel)
                    }

                    KeyboardLanguage.FRENCH.name -> {
                        for (key in nRowKeys.row2KeysFr) KeyboardKey(key, keyWidth, viewModel)
                    }

                    else -> {
                        for (key in nRowKeys.row2Keys) KeyboardKey(key, keyWidth, viewModel)
                    }
                }
            }
        }
        Box(
            Modifier
                .layoutId('3')
        ) {
            ConstraintLayout(
                when (locale) {
                    KeyboardLanguage.FRENCH.name -> nConst.thirdRowConstraintsFr
                    else -> nConst.thirdRowConstraints
                },
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                100, true
            ) {
                when (locale) {
                    KeyboardLanguage.FRENCH.name -> {
                        for (key in nRowKeys.row3KeysFr) {
                            if (key.id == "shift" || key.id == "erase") {
                                KeyboardKey(key, keyWidthSE, viewModel)
                            } else KeyboardKey(key, keyWidth, viewModel)
                        }
                    }

                    else -> {
                        for (key in nRowKeys.row3Keys) {
                            if (key.id == "shift" || key.id == "erase") {
                                KeyboardKey(key, keyWidthSE, viewModel)
                            } else KeyboardKey(key, keyWidth, viewModel)
                        }
                    }
                }
            }
        }
        Box(
            Modifier
                .layoutId('4')
        ) {
            ConstraintLayout(
                nConst.fourthRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp), 100, true
            ) {
                KeyboardKey(Key("123", stringResource(R.string.num)), keyWidthM, viewModel)
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