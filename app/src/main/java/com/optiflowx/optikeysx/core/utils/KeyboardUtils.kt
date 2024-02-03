package com.optiflowx.optikeysx.core.utils

import android.content.Context
import android.graphics.Rect
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.scale
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.core.widgets.Optimizer
import androidx.core.content.ContextCompat
import com.optiflowx.optikeysx.core.enums.KeyboardFontType
import com.optiflowx.optikeysx.ui.bold
import com.optiflowx.optikeysx.ui.medium
import com.optiflowx.optikeysx.ui.regular

const val OPTIMIZATION_STANDARDIZED =
    (Optimizer.OPTIMIZATION_DIRECT
            or Optimizer.OPTIMIZATION_CACHE_MEASURES or Optimizer.OPTIMIZATION_CHAIN)

@Stable
val TextUnit.nonScaledSp
    @Composable
    get() = (this.value / LocalDensity.current.fontScale).sp


@Stable
fun Modifier.draw9Patch(
    context: Context,
    @DrawableRes res: Int,
) = this.drawBehind {
    drawIntoCanvas {
        ContextCompat.getDrawable(context, res)?.let { ninePatch ->
            ninePatch.run {
                bounds = Rect(0, 0, size.width.toInt(), size.height.toInt())
                draw(it.nativeCanvas)
            }
        }
    }
}

@Stable
fun appFontType(fontType: String?): FontFamily {
    return when(fontType) {
        KeyboardFontType.Bold.name -> bold
        KeyboardFontType.Medium.name -> medium
        KeyboardFontType.Regular.name -> regular
        else -> regular
    }
}

@Stable
fun Modifier.boxShadow(
    color: Color = Color.Black,
    alpha: Float = 0.07f,
    borderRadius: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 7.dp,
    spread: Dp = 0.dp,
    enabled: Boolean = true,
) = if(enabled) {
    this.drawBehind {
        val transparentColor = color.copy(0.0f).toArgb()
        val shadowColor = color.copy(alpha).toArgb()
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparentColor
            frameworkPaint.setShadowLayer(
                blurRadius.toPx(),
                offsetX.toPx(),
                offsetY.toPx(),
                shadowColor
            )
            it.save()

            if(spread.value > 0) {
                fun calcSpreadScale(spread: Float, childSize: Float): Float {
                    return 1f + ((spread / childSize) * 2f)
                }

                it.scale(
                    calcSpreadScale(spread.toPx(), this.size.width),
                    calcSpreadScale(spread.toPx(), this.size.height),
                    this.center.x,
                    this.center.y
                )
            }

            it.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height,
                borderRadius.toPx(),
                borderRadius.toPx(),
                paint
            )
            it.restore()
        }
    }
} else this