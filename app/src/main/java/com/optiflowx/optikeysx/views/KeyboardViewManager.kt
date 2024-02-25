package com.optiflowx.optikeysx.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.optikeysx.core.data.KeyboardData
import com.optiflowx.optikeysx.core.enums.KeyboardType
import com.optiflowx.optikeysx.core.utils.appFontType
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ui.AppleKeyboardIMETheme
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import com.optiflowx.optikeysx.views.defaults.DefaultLandscapeKeyboard
import com.optiflowx.optikeysx.views.defaults.DefaultPortraitKeyboard
import com.optiflowx.optikeysx.views.number.NumberLandscapeKeyboard
import com.optiflowx.optikeysx.views.number.NumberPortraitKeyboard
import com.optiflowx.optikeysx.views.phone.PhoneLandscapeKeyboard
import com.optiflowx.optikeysx.views.phone.PhonePortraitKeyboard
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray
import splitties.experimental.ExperimentalSplittiesApi
import splitties.views.InputType

@SuppressLint("ViewConstructor")
class KeyboardViewManager(context: Context, private val data: KeyboardData) :
    AbstractComposeView(context) {

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    @OptIn(ExperimentalSplittiesApi::class)
    @Composable
    override fun Content() {

        val config = LocalConfiguration.current

        val orientation = rememberSaveable(config.orientation) {
            mutableIntStateOf(config.orientation)
        }

        val viewModel = viewModel<KeyboardViewModel>(
            key = "KeyboardViewModel",
            factory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return KeyboardViewModel(context) as T
                }
            }
        )

        val prefs = viewModel.prefs

        val isAuth = prefs.isAuthenticated.observeAsState()

        val fontType = prefs.keyboardFontType.observeAsState().value

        val kType = viewModel.keyboardType.collectAsState().value

        LaunchedEffect(data) {
            viewModel.initKeyboardData(data)

            viewModel.updateSuggestionsState()

            if(kType == KeyboardType.Recognizer) {
                viewModel.onUpdateKeyboardType(KeyboardType.Normal)
            }

            if (viewModel.prefs.autoSwitchIBackIME.get()) {
                viewModel.onUpdateKeyboardType(KeyboardType.Normal)
            }
        }

        DisposableEffect(Unit) {
            viewModel.initSoundIDs(context)

            onDispose { viewModel.onDisposeSoundIDs() }
        }

        AppleKeyboardIMETheme {
            Surface(
                shape = RectangleShape,
                modifier = Modifier.wrapContentSize(),
                color = MaterialTheme.colorScheme.background.copy(
                    alpha = 1f
                ),
            ) {
                if (isAuth.value) {
                    when (data.inputType) {
                        InputType.number.value -> {
                            if (orientation.intValue == Configuration.ORIENTATION_PORTRAIT) {
                                NumberPortraitKeyboard(viewModel)
                            } else NumberLandscapeKeyboard(viewModel)
                        }

                        InputType.phone.value -> {
                            if (orientation.intValue == Configuration.ORIENTATION_PORTRAIT) {
                                PhonePortraitKeyboard(viewModel)
                            } else PhoneLandscapeKeyboard(viewModel)
                        }

                        else -> {
                            if (orientation.intValue == Configuration.ORIENTATION_PORTRAIT) {
                                DefaultPortraitKeyboard(viewModel)
                            } else DefaultLandscapeKeyboard(viewModel)
                        }
                    }
                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            viewModel.keyboardLocale.authentication(),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                color = CupertinoColors.systemGray(isSystemInDarkTheme()),
                                fontFamily = appFontType(fontType),
                                fontSize = TextUnit(15f, TextUnitType.Sp).nonScaledSp,
                                platformStyle = PlatformTextStyle(includeFontPadding = false)
                            ),
                            modifier = Modifier.padding(15.dp),
                        )
                    }
                }
            }

            isSystemInDarkTheme()
        }
    }
}
