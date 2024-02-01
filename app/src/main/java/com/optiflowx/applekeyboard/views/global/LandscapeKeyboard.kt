package com.optiflowx.applekeyboard.views.global

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.core.enums.KeyboardType
import com.optiflowx.applekeyboard.core.preferences.PrefsConstants
import com.optiflowx.applekeyboard.core.preferences.rememberPreference
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.clipboard.ClipboardKeyboardView
import com.optiflowx.applekeyboard.views.emoji.EmojiKeyboardView
import com.optiflowx.applekeyboard.views.normal.NormalKeyboardView
import com.optiflowx.applekeyboard.views.number.NumberKeyboardView
import com.optiflowx.applekeyboard.views.phone.PhoneNumberKeyboardView
import com.optiflowx.applekeyboard.views.symbols.SymbolsKeyboardView

@Composable
fun LandscapeKeyboard(
    keyboardType: State<KeyboardType?>,
    viewModel: KeyboardViewModel,
) {
    val showTopView = (keyboardType.value != KeyboardType.Symbol)

    val showSideViews = (keyboardType.value != KeyboardType.Number
            && keyboardType.value != KeyboardType.Phone)

    val fontType  by rememberPreference(PrefsConstants.FONT_TYPE_KEY, "Regular")

    val locale  by rememberPreference(PrefsConstants.LOCALE_KEY, "English")

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val viewWidth = (screenWidth * 0.8)
    val sideWidth = (screenWidth * 0.1)

    Box(
        Modifier
            .mandatorySystemGesturesPadding()
    ) {

        KeyboardOptionsView(viewModel, locale, fontType, viewWidth, 32, 14)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (showTopView) {
                KeyboardTopView(
                    viewModel = viewModel,
                    locale = locale,
                    keyboardType = keyboardType,
                    viewWidth = viewWidth,
                    topViewHeight = 32,
                    textSize = 14f,
                    searchIconSize = 16,
                )
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(vertical = 2.dp)
            ) {

                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .width(sideWidth.dp)
                        .padding(bottom = 2.dp)
                ) {
                    if(showSideViews) {
                        Icon(
                            painter = painterResource(R.drawable.globe_outline),
                            contentDescription = "globe",
                            tint = MaterialTheme.colorScheme.scrim,
                            modifier = Modifier
                                .size(28.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    role = Role.Button,
                                ) { viewModel.updateIsShowOptions(true) }
                        )
                    }
                }

                when (keyboardType.value!!) {
                    KeyboardType.Normal -> NormalKeyboardView(
                        viewModel,
                        viewWidth.dp,
                        30.dp,
                        36.dp
                    )

                    KeyboardType.Symbol -> SymbolsKeyboardView(
                        viewModel,
                        viewWidth.dp,
                        30.dp,
                        36.dp
                    )

                    KeyboardType.Number -> NumberKeyboardView(viewModel, viewWidth.dp, 36, 8)

                    KeyboardType.Phone -> PhoneNumberKeyboardView(viewModel, viewWidth.dp, 36, 8)

                    KeyboardType.Emoji -> EmojiKeyboardView(viewModel, viewWidth.dp, 150.dp, 9)

                    KeyboardType.Clipboard -> ClipboardKeyboardView(
                        viewModel,
                        viewWidth.dp,
                        150
                    )
                }


                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .width(sideWidth.dp)
                        .padding(bottom = 2.dp)
                ) {
                    if (showSideViews) {
                        Icon(
                            painter = painterResource(
                                if (isSystemInDarkTheme()) R.drawable.mic_fill else R.drawable.mic_outline
                            ),
                            contentDescription = "microphone",
                            tint = MaterialTheme.colorScheme.scrim,
                            modifier = Modifier
                                .size(28.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    role = Role.Button,
                                ) { }
                        )
                    }

                }
            }
        }
    }
}