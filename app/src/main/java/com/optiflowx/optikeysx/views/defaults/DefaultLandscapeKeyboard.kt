package com.optiflowx.optikeysx.views.defaults

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.mandatorySystemGesturesPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.enums.KeyboardType
import com.optiflowx.optikeysx.core.utils.OPTIMIZATION_STANDARDIZED
import com.optiflowx.optikeysx.ui.keyboard.KeyboardGlobalOptions
import com.optiflowx.optikeysx.ui.keyboard.KeyboardTopView
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import com.optiflowx.optikeysx.views.clipboard.ClipboardKeyboardView
import com.optiflowx.optikeysx.views.emoji.EmojiKeyboardView
import com.optiflowx.optikeysx.views.keyboards.french.FrenchKeyboardView
import com.optiflowx.optikeysx.views.keyboards.portuguese.PortugueseKeyboardView
import com.optiflowx.optikeysx.views.keyboards.russian.RussianKeyboardView
import com.optiflowx.optikeysx.views.keyboards.spanish.SpanishKeyboardView
import com.optiflowx.optikeysx.views.keyboards.standard.StandardKeyboardView
import com.optiflowx.optikeysx.views.recognition.VoiceRecognitionView
import com.optiflowx.optikeysx.views.symbols.SymbolsKeyboardView
import dev.patrickgold.jetpref.datastore.model.observeAsState

@Composable
fun DefaultLandscapeKeyboard(
    vM: KeyboardViewModel
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val isPredictive = vM.prefs.isPredictive.observeAsState().value
    val vW = (screenWidth * 0.76).dp

    val constraintsSet = ConstraintSet {
        val topView = createRefFor("topView")
        val keyboardView = createRefFor("keyboardView")

        constrain(topView) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(keyboardView) {
            top.linkTo(topView.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
    }

    val fontType = vM.prefs.keyboardFontType.observeAsState().value
    val keyboardType = vM.keyboardType.collectAsState()
    val locale = vM.keyboardData.collectAsState().value.locale

    ConstraintLayout(
        constraintSet = constraintsSet,
        modifier = Modifier
            .mandatorySystemGesturesPadding()
            .wrapContentSize(),
        optimizationLevel = OPTIMIZATION_STANDARDIZED,
        animateChanges = true,
    ) {
        if(isPredictive) {
            KeyboardTopView(vM, vW, 32, 16, 14f)
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .layoutId("keyboardView")
                .width(screenWidth.dp)
                .padding(vertical = 2.dp)
        ) {
            Box {
                KeyboardGlobalOptions(vM, fontType, vW, 32, 14, 30)
                SideView(
                    icon = painterResource(R.drawable.globe_outline),
                    onClick = { vM.updateIsShowOptions(true) }
                )
            }

            Box(
                modifier = Modifier.wrapContentSize(), Alignment.Center
            ) {
                when (keyboardType.value) {
                    KeyboardType.Normal -> {
                        when (locale) {
                            "pt-BR" -> PortugueseKeyboardView(vM, vW, 30.dp, 36.dp)
                            "pt-PT" -> PortugueseKeyboardView(vM, vW, 30.dp, 36.dp)
                            "fr-FR" -> FrenchKeyboardView(vM, vW, 30.dp, 36.dp)
                            "es" -> SpanishKeyboardView(vM, vW, 30.dp, 36.dp)
                            "ru" -> RussianKeyboardView(vM, vW, 30.dp, 36.dp)
                            else -> StandardKeyboardView(vM, vW, 30.dp, 36.dp)
                        }
                    }

                    KeyboardType.Symbol -> SymbolsKeyboardView(vM, vW, 30.dp, 36.dp)

                    KeyboardType.Emoji -> EmojiKeyboardView(vM, vW, 150.dp, 9)

                    KeyboardType.Recognizer -> VoiceRecognitionView(vM, vW, 150.dp)

                    KeyboardType.Clipboard -> ClipboardKeyboardView(vM, vW, 150.dp)
                }
            }

            SideView(
                icon = painterResource(
                    if (isSystemInDarkTheme()) R.drawable.mic_fill else R.drawable.mic_outline
                ),
                onClick = {}
            )
        }
    }
}

@Composable
fun SideView(
    icon: Painter,
    onClick: () -> Unit = {}
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val sideWidth = (screenWidth * 0.12).dp

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .width(sideWidth)
            .padding(bottom = 2.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = "icon",
            tint = MaterialTheme.colorScheme.scrim,
            modifier = Modifier
                .size(28.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    role = Role.Button,
                    onClick = onClick,
                )
        )
    }
}