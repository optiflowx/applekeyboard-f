package com.optiflowx.optikeysx.screens.home.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.optikeysxPreferences
import com.optiflowx.optikeysx.screens.KeyboardFontsScreen
import com.optiflowx.optikeysx.screens.KeyboardsScreen
import com.optiflowx.optikeysx.screens.featurerequest.FeatureRequestsScreen
import com.optiflowx.optikeysx.screens.speech.VoiceRecognitionSettingsScreen
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.SectionLink
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun GeneralSection(
    titleTextStyle: TextStyle,
    tileTextStyle: TextStyle,
) {
    val navigator = LocalNavigator.currentOrThrow
    val prefs by optikeysxPreferences()
    val context = LocalContext.current

    CupertinoSection(
        title = { CupertinoText(stringResource(R.string.general), style = titleTextStyle) },
    ) {
        SectionLink(
            title = {
                CupertinoText(
                    stringResource(R.string.keyboards), style = tileTextStyle,
                )
            },
            onClick = { navigator.push(KeyboardsScreen()) }
        )
        SectionLink(
            title = {
                CupertinoText(
                    stringResource(R.string.keyboard_fonts),
                    style = tileTextStyle
                )
            },
            onClick = { navigator.push(KeyboardFontsScreen()) }
        )
        SectionLink(
            title = {
                CupertinoText(
                    stringResource(R.string.feature_request),
                    style = tileTextStyle
                )
            },
            onClick = { navigator.push(FeatureRequestsScreen()) }
        )
        SectionLink(
            title = {
                CupertinoText(
                    stringResource(R.string.speech_keyboard),
                    style = tileTextStyle,
                    color = if (prefs.isEnableSpeechRecognition.observeAsState().value)
                        Color.Unspecified else CupertinoColors.systemGray
                )
            },
            onClick = {
                if (prefs.isEnableSpeechRecognition.get()) {
                    navigator.push(VoiceRecognitionSettingsScreen())
                } else {
                    Toast.makeText(
                        context,
                        R.string.enable_speech_recognition_all,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
//        SectionLink(
//            onClickLabel = "Text Replacement",
//            enabled = false,
//            title = {
//                CupertinoText("Text Replacement", style = tileTextStyle,
//                    color = CupertinoColors.systemRed)
//            },
//            onClick = {
////                navigator.push(TextReplacementScreen())
//            }
//        )
    }
}