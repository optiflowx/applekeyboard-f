package com.optiflowx.applekeyboard.composables.keyboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Composable
fun KeyButton(
    onClick: () -> Unit,
    elevation: Dp = 3.dp,
    color: Color,
    buttonWidth: Dp,
    id: String,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val constraints = ConstraintSet() {
        val view = createRefFor("content")
        constrain(view) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.value(buttonWidth)
        }
    }

    Surface(
        color = color,
        tonalElevation = elevation,
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .layoutId(id)
            .width(buttonWidth)
            .clickable (
                enabled = true,
                indication = null,
                interactionSource = interactionSource,
                onClick = onClick,
                role = Role.Button,
            ),
        ) {
        ConstraintLayout(constraints, optimizationLevel = 100, animateChanges = true) {
            Box(
                Modifier
                    .layoutId("content")
                    .fillMaxSize(), Alignment.Center
            ) {
                content()
            }
        }
    }
}
