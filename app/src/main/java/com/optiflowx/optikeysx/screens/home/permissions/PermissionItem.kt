package com.optiflowx.optikeysx.screens.home.permissions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.CheckmarkCircle
import io.github.alexzhirkevich.cupertino.icons.outlined.XmarkCircle
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import io.github.alexzhirkevich.cupertino.theme.Gray
import io.github.alexzhirkevich.cupertino.theme.systemBlue
import io.github.alexzhirkevich.cupertino.theme.systemGreen
import io.github.alexzhirkevich.cupertino.theme.systemRed

@Composable
fun PermissionItem(
    permission: String,
    isGranted: Boolean,
    imageVector: ImageVector,
    explanation: String,
    howItWorks: String
) {
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        CupertinoIcon(
            imageVector = if (isGranted) CupertinoIcons.Outlined.CheckmarkCircle
            else CupertinoIcons.Outlined.XmarkCircle,
            contentDescription = "State Icon",
            tint = if (isGranted) CupertinoColors.systemGreen
            else CupertinoColors.systemRed,
            modifier = Modifier
                .size(18.dp)
                .align(Alignment.TopEnd)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CupertinoIcon(
                imageVector = imageVector,
                contentDescription = "Permission Icon",
                tint = CupertinoColors.systemBlue,
                modifier = Modifier.size(30.dp)
            )
            Spacer(Modifier.width(15.dp))
            Column {
                CupertinoText(
                    text = permission,
                    style = CupertinoTheme.typography.title3,
                )
                CupertinoText(
                    text = "$explanation $howItWorks",
                    style = CupertinoTheme.typography.subhead,
                    color = CupertinoColors.Gray,
                )
            }
            Spacer(Modifier.width(8.dp))

        }
    }
}