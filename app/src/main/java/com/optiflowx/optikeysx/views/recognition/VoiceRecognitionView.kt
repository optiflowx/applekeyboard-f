package com.optiflowx.optikeysx.views.recognition

import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.linc.audiowaveform.AudioWaveform
import com.linc.audiowaveform.model.AmplitudeType
import com.optiflowx.optikeysx.core.enums.KeyboardFontType
import com.optiflowx.optikeysx.core.enums.RecognitionState
import com.optiflowx.optikeysx.core.utils.appFontType
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ime.IMEService
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import com.optiflowx.optikeysx.viewmodels.VoiceRecognitionViewModel
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray
import kotlinx.coroutines.launch

@Composable
fun VoiceRecognitionView(
    keyboardViewModel: KeyboardViewModel,
    viewWidth: Dp,
    viewHeight: Dp = 250.dp,
) {
    val context = LocalContext.current
    val ime = context as IMEService

    val viewModel = viewModel<VoiceRecognitionViewModel>(
        key = "VoiceRecognitionViewModel",
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return VoiceRecognitionViewModel(ime) as T
            }
        }
    )

    val amplitudes = viewModel.amplitudes.collectAsState().value.toList()
    val state = viewModel.stateLD.observeAsState().value

    @Composable
    fun myText(text: String) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = CupertinoColors.systemGray(isSystemInDarkTheme()),
                fontFamily = appFontType(KeyboardFontType.Medium),
                fontSize = TextUnit(15f, TextUnitType.Sp).nonScaledSp,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            modifier = Modifier.padding(15.dp),
        )
    }

    LaunchedEffect(state) {
        launch {
            if (state == RecognitionState.READY) {
                viewModel.start()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.pause(true).let {
                viewModel.stop(true)
            }
        }
    }

    Box(
        modifier = Modifier
            .width(viewWidth)
            .height(viewHeight),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            RecognitionState.NO_MODEL -> {
                myText(keyboardViewModel.keyboardLocale.noModel())
            }

            RecognitionState.NO_MIC_PERMISSION -> {
                myText(keyboardViewModel.keyboardLocale.noMicPermission())
            }

            RecognitionState.RECOGNIZER_ERROR -> {
                myText(keyboardViewModel.keyboardLocale.recognizerError())
            }

            RecognitionState.READY -> {
                myText(keyboardViewModel.keyboardLocale.startingInAMoment())
            }

            RecognitionState.LOADING -> {
                myText(keyboardViewModel.keyboardLocale.loading())
            }

            RecognitionState.INITIAL -> {
                myText(keyboardViewModel.keyboardLocale.initializing())
            }

            else -> {
                AudioWaveform(
                    spikeRadius = 5.dp,
                    spikePadding = 1.5.dp,
                    spikeWidth = 3.5.dp,
                    waveformBrush = SolidColor(MaterialTheme.colorScheme.scrim),
                    progressBrush = SolidColor(Color.Transparent),
                    amplitudes = amplitudes,
                    amplitudeType = AmplitudeType.Max,
                    spikeAnimationSpec = tween(400),
                    onProgressChange = { _ -> }
                )
            }
        }
    }
}

