package com.optiflowx.applekeyboard.utils

import android.content.Context
import android.graphics.Rect
import androidx.annotation.DrawableRes
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.core.content.ContextCompat

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