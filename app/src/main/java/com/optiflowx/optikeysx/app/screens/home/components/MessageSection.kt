package com.optiflowx.optikeysx.app.screens.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ime.theme.regular
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
                text = stringResource(R.string.caution_unofficial_sources),
                style = TextStyle(
                    fontSize = 14.sp.nonScaledSp,
                    fontFamily = regular,
                    color = CupertinoColors.systemRed,
                ),
            )
        }
    }
}