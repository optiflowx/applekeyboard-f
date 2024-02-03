package com.optiflowx.optikeysx.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp

@Stable
val keyShapeValue = 5.5.dp

@Stable
val boxShapeValue = 12.dp

@Stable
val keyShape = Shapes(small = RoundedCornerShape(keyShapeValue))

@Stable
val boxShape = Shapes(small = RoundedCornerShape(boxShapeValue))
