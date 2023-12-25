package com.optiflowx.applekeyboard.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun KeyButton(
    onClick: () -> Unit,
    onDoubleClick: (() -> Unit)?,
    elevation: Dp = 2.dp,
    color: Color,
    buttonWidth: Dp,
    id: String,
    content: @Composable () -> Unit,
) {

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
        elevation = elevation,
        modifier = Modifier
            .layoutId(id)
            .width(buttonWidth)
            .combinedClickable(
                onClick = onClick,
                onDoubleClick = onDoubleClick,
                role = Role.Button,
            ),
        shape = RoundedCornerShape(6.dp),
        color = color,
    ) {
        ConstraintLayout(constraints, optimizationLevel = 100, animateChanges = true) {
            Box(
                Modifier
                    .layoutId("content")
                    .fillMaxSize(), Alignment.Center) {
                content()
            }
        }
    }
}
