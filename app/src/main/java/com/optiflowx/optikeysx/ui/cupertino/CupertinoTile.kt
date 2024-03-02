package com.optiflowx.optikeysx.ui.cupertino

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.ui.regular
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.ChevronForward
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGray

@Composable
fun CupertinoTile(
    title: String,
    titleColor: Color = Color.Unspecified,
    trailingText: String = "",
    trailingTint: Color = CupertinoColors.systemGray(isSystemInDarkTheme()),
    trailingIcon: ImageVector? = CupertinoIcons.Outlined.ChevronForward,
    padding: Int = 15,
    trailingIconSize: Int = 12,
    onClick: () -> Unit = {},
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(padding.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CupertinoText(
            title,
            style = TextStyle(
                color = titleColor,
                fontFamily = regular,
                fontSize = TextUnit(17f, TextUnitType.Sp).nonScaledSp,
            )
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            CupertinoText(
                trailingText,
                color = trailingTint,
                fontFamily = regular,
                fontSize = TextUnit(14f, TextUnitType.Sp).nonScaledSp
            )
            AnimatedVisibility(trailingIcon != null) {
                CupertinoIcon(
                    imageVector = trailingIcon!!,
                    contentDescription = "chevron",
                    modifier = Modifier.size(trailingIconSize.dp),
                    tint = trailingTint
                )
            }
        }
    }
}