package com.optiflowx.optikeysx.ime.theme

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class PopupShapeLeft(
    private val inWidth: Dp = 60.dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val height = 105.dp
        val width = inWidth + (inWidth * 0.5f)

        val viewHeight = with(density) { height.toPx() }
        val viewWidth = with(density) { width.toPx() * 0.85f }

        val cornerRadius = with(density) { 5.5.dp.toPx() }
        val radiusTop = with(density) { 8.dp.toPx() }

        val topExpandableViewHeight = with(density) { 48.dp.toPx() }
        val bottomViewHeight = with(density) { 42.dp.toPx() }


        val tiltHeight = viewHeight - topExpandableViewHeight - bottomViewHeight
        val tiltWidth = 0f
        val bottomLine = viewWidth * 0.85.toFloat()

        val path = Path().apply {
            moveTo(tiltWidth + radiusTop, 0f) //corner
            this.quadraticBezierTo(
                tiltWidth, 0f,
                tiltWidth, radiusTop,
            )
            lineTo(tiltWidth, viewHeight - cornerRadius)
            this.quadraticBezierTo(
                tiltWidth, viewHeight,
                tiltWidth + cornerRadius, viewHeight,
            )
            lineTo(bottomLine - cornerRadius, viewHeight)
            this.quadraticBezierTo(
                bottomLine, viewHeight,
                bottomLine, viewHeight - cornerRadius,
            )
            lineTo(bottomLine, viewHeight - bottomViewHeight) //corner 1
            lineTo(viewWidth, viewHeight - bottomViewHeight - tiltHeight) //corner 2
            lineTo(viewWidth, radiusTop) //corner
            this.quadraticBezierTo(
                viewWidth, 0f,
                viewWidth - radiusTop, 0f,
            )

            close()
        }

        return Outline.Generic(path)
    }
}

class PopupShape(
    private val inWidth: Dp = 60.dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val height = 105.dp
        val width = inWidth + (inWidth * 0.5f)

        val viewHeight = with(density) { height.toPx() }
        val viewWidth = with(density) { width.toPx() }

        val cornerRadius = with(density) { 5.5.dp.toPx() }

        val topExpandableViewHeight = with(density) { 48.dp.toPx() }
        val bottomViewHeight = with(density) { 42.dp.toPx() }


        val tiltHeight = viewHeight - topExpandableViewHeight - bottomViewHeight
        val tiltWidth = viewWidth * 0.15.toFloat()
        val bottomLine = viewWidth * 0.85.toFloat()
        val tiltHeightInView = topExpandableViewHeight + tiltHeight

        val path = Path().apply {

            moveTo(cornerRadius, 0f) //corner
            this.quadraticBezierTo(
                0f, 0f,
                0f, cornerRadius,
            )
            lineTo(0f, topExpandableViewHeight) //corner
            lineTo(tiltWidth, tiltHeightInView) // corner
            lineTo(tiltWidth, viewHeight - cornerRadius) //corner
            this.quadraticBezierTo(
                tiltWidth, viewHeight,
                tiltWidth + cornerRadius, viewHeight,
            )
            lineTo(bottomLine - cornerRadius, viewHeight) //corner
            this.quadraticBezierTo(
                bottomLine, viewHeight,
                bottomLine, viewHeight - cornerRadius,
            )
            lineTo(bottomLine, viewHeight - bottomViewHeight) //corner
            lineTo(viewWidth, viewHeight - bottomViewHeight - tiltHeight) //corner
//                    lineTo(bottomLine, viewHeight - bottomViewHeight + cornerRadius) //corner
//                    this.quadraticBezierTo(
//                        bottomLine, viewHeight - bottomViewHeight,
//                        bottomLine, viewHeight - bottomViewHeight,
//                    )
//                    lineTo(viewWidth - cornerRadius, viewHeight - bottomViewHeight - tiltHeight) //corner
//                    this.quadraticBezierTo(
//                        viewWidth, viewHeight - bottomViewHeight - tiltHeight,
//                        viewWidth, viewHeight - bottomViewHeight - tiltHeight - cornerRadius,
//                    )
            lineTo(viewWidth, cornerRadius) //corner
            this.quadraticBezierTo(
                viewWidth, 0f,
                viewWidth - cornerRadius, 0f,
            )

            close()
        }

        return Outline.Generic(path)
    }
}

class PopupShapeRight(
    private val inWidth: Dp = 60.dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val height = 105.dp
        val width = (inWidth + (inWidth * 0.5f))

        val viewHeight = with(density) { height.toPx() }
        val viewWidth = with(density) { width.toPx() * 0.85f}

        val cornerRadius = with(density) { 5.5.dp.toPx() }
        val radiusTop = with(density) { 8.dp.toPx() }

        val topExpandableViewHeight = with(density) { 48.dp.toPx() }
        val bottomViewHeight = with(density) { 42.dp.toPx() }


        val tiltHeight = viewHeight - topExpandableViewHeight - bottomViewHeight
        val tiltWidth = viewWidth * 0.15.toFloat()
        val bottomLine = viewWidth + tiltWidth - 3
        val tiltHeightInView = topExpandableViewHeight + tiltHeight

        val path = Path().apply {
            moveTo(radiusTop, 0f) //corner
            this.quadraticBezierTo(
                0f , 0f,
                0f , radiusTop,
            )
            lineTo(0f, topExpandableViewHeight) //corner
            lineTo(tiltWidth * 2, tiltHeightInView) // corner
            lineTo(tiltWidth * 2, viewHeight - cornerRadius) //corner
            this.quadraticBezierTo(
                tiltWidth * 2, viewHeight,
                tiltWidth * 2 + cornerRadius, viewHeight,
            )
            lineTo(bottomLine - cornerRadius, viewHeight) //corner
            this.quadraticBezierTo(
                bottomLine, viewHeight,
                bottomLine, viewHeight - cornerRadius,
            )
            lineTo(bottomLine, viewHeight - bottomViewHeight) //corner
            lineTo(bottomLine, radiusTop) //corner
            this.quadraticBezierTo(
                bottomLine, 0f,
                bottomLine - radiusTop, 0f,
            )

            close()
        }

        return Outline.Generic(path)
    }
}