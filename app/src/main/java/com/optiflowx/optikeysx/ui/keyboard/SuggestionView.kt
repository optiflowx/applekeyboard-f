package com.optiflowx.optikeysx.ui.keyboard

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.optiflowx.optikeysx.core.utils.OPTIMIZATION_STANDARDIZED
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import dev.patrickgold.jetpref.datastore.model.observeAsState

@Composable
fun SuggestionView(viewModel: KeyboardViewModel, textSize: Float, boxScope: BoxScope) {
    val context = LocalContext.current
    val suggestions = viewModel.wordsDictionary.collectAsState().value

    val suggestion1 = remember(suggestions) {
        mutableStateOf(if (suggestions.isNotEmpty()) suggestions.elementAt(0) else "")
    }

    val suggestion2 = remember(suggestions) {
        mutableStateOf(if (suggestions.size >= 2) suggestions.elementAt(1) else "")
    }

    val suggestion3 = remember(suggestions) {
        mutableStateOf(if (suggestions.size >= 3) suggestions.elementAt(2) else "")
    }

    val fontType = viewModel.prefs.keyboardFontType.observeAsState().value


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
        if (suggestions.isNotEmpty()) {
            suggestions.forEachIndexed { index, element ->
                when (index) {
                    0 -> suggestion1.value = element
                    1 -> suggestion2.value = element
                    2 -> suggestion3.value = element
                }
            }
        }
    }

    boxScope.apply {
        ConstraintLayout(
            constraintSet = constraints,
            modifier = Modifier.matchParentSize(),
            optimizationLevel = OPTIMIZATION_STANDARDIZED,
            animateChanges = true,
            animationSpec = tween(300),
        ) {
            Suggestion("sug1", suggestion1.value, fontType, textSize, onClick = {
                viewModel.onSuggestionClick(
                    suggestion1.value,
                    context
                )
            })
            Div("div1")
            Suggestion("sug2", suggestion2.value, fontType, textSize, onClick = {
                viewModel.onSuggestionClick(
                    suggestion2.value,
                    context
                )
            })
            Div("div2")
            Suggestion("sug3", suggestion3.value, fontType, textSize, onClick = {
                viewModel.onSuggestionClick(
                    suggestion3.value,
                    context
                )
            })
        }
    }
}