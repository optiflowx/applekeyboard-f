package com.optiflowx.applekeyboard.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.optiflowx.applekeyboard.composables.Div
import com.optiflowx.applekeyboard.composables.Suggestion

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun SuggestionView() {
    val viewWidth = LocalConfiguration.current.screenWidthDp.dp

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

    Box(
        Modifier
            .width(viewWidth)
            .fillMaxSize()
    ) {
        ConstraintLayout(
            constraints,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center)
        ) {
            Suggestion("sug1")
            Div("div1")
            Suggestion("sug2")
            Div("div2")
            Suggestion("sug3")
        }
    }
}