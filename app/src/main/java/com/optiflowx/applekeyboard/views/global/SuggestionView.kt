package com.optiflowx.applekeyboard.views.global

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.optiflowx.applekeyboard.core.preferences.PreferencesConstants
import com.optiflowx.applekeyboard.ui.keyboard.Div
import com.optiflowx.applekeyboard.ui.keyboard.Suggestion
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel

@Composable
fun SuggestionView(viewModel: KeyboardViewModel, viewWidth: Double, textSize : Float) {
    val context = LocalContext.current
    val suggestions = viewModel.wordsDictionary.observeAsState().value

    val suggestion1 = remember(suggestions) {
        mutableStateOf(if (suggestions?.isNotEmpty() == true) suggestions.elementAt(0) else "")
    }

    val suggestion2 = remember(suggestions) {
        mutableStateOf(if (suggestions != null && suggestions.size >= 2) suggestions.elementAt(1) else "")
    }

    val suggestion3 = remember(suggestions) {
        mutableStateOf(if (suggestions != null && suggestions.size >= 3) suggestions.elementAt(2) else "")
    }

    val fontType = viewModel.preferences.getFlowPreference(PreferencesConstants.FONT_TYPE_KEY, "Regular").collectAsStateWithLifecycle(
        "Regular")

    val constraints = ConstraintSet {
        val firstSuggestion = createRefFor("sug1")
        val secondSuggestion = createRefFor("sug2")
        val thirdSuggestion = createRefFor("sug3")
        val div1 = createRefFor("div1")
        val div2 = createRefFor("div2")

        constrain(firstSuggestion) {
            start.linkTo(parent.start)
            end.linkTo(div1.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }

        constrain(div1) {
            start.linkTo(firstSuggestion.end)
            end.linkTo(secondSuggestion.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }

        constrain(secondSuggestion) {
            start.linkTo(div1.end)
            end.linkTo(div2.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }

        constrain(div2) {
            start.linkTo(secondSuggestion.end)
            end.linkTo(thirdSuggestion.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }

        constrain(thirdSuggestion) {
            start.linkTo(div2.end)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
    }

    LaunchedEffect(suggestions) {
        if (suggestions?.isNotEmpty() == true) {
            suggestions.forEachIndexed { index, element ->
                when (index) {
                    0 -> suggestion1.value = element
                    1 -> suggestion2.value = element
                    2 -> suggestion3.value = element
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(viewWidth.dp)
            .fillMaxHeight()

    ) {
        ConstraintLayout(
            constraints,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center), 100, true
        ) {
            Suggestion("sug1", suggestion1.value,fontType.value,textSize, onClick = {
                viewModel.onSuggestionClick(
                    suggestion1.value,
                    context
                )
            })
            Div("div1")
            Suggestion("sug2", suggestion2.value,fontType.value, textSize, onClick = {
                viewModel.onSuggestionClick(
                    suggestion2.value,
                    context
                )
            })
            Div("div2")
            Suggestion("sug3", suggestion3.value,fontType.value, textSize, onClick = {
                viewModel.onSuggestionClick(
                    suggestion3.value,
                    context
                )
            })
        }
    }
}