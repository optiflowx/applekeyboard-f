package com.optiflowx.optikeysx.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ui.regular
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.SectionItem
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemRed

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun MessageSection() {
    CupertinoSection {
        SectionItem {
            CupertinoText(
                text = "CAUTION: Acquiring this application from unofficial sources may expose " +
                        "you to potential malware threats. Thus, I strongly advise against accepting or using any " +
                        "modified versions of this app. Download the app from the Google Play Store.",
                style = TextStyle(
                    fontSize = 14.sp.nonScaledSp,
                    fontFamily = regular,
                    color = CupertinoColors.systemRed,
                ),
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}