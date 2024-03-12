package com.optiflowx.optikeysx.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ui.bold
import com.optiflowx.optikeysx.ui.regular
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoButtonDefaults
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemGreen
import io.github.alexzhirkevich.cupertino.theme.systemRed

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun PremiumSection(isPremium: Boolean) {



    CupertinoSection(
        containerColor = if(isPremium) CupertinoColors.systemGreen else CupertinoColors.systemRed,
        color = Color.Transparent
    ) {

        item("box") {
            AnimatedContent(targetState = isPremium, label = "isPremium") {
                if (it) {
                    CupertinoText(
                        text = "You have premium access, all features are unlocked for you!",

                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp.nonScaledSp,
                            fontFamily = regular,
                        ),
                    )
                } else {
                    Column {
                        CupertinoText(
                            text = "You are not a premium member, some or all features may be locked. " +
                                    "To unlock all features, please consider purchasing a premium membership.",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 16.sp.nonScaledSp,
                                fontFamily = regular,
                            ),
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            CupertinoButton(
                                colors = CupertinoButtonDefaults.borderedButtonColors(
                                    contentColor = Color.White
                                ),
                                onClick = {

                                }
                            ) {
                                CupertinoText(
                                    text = "Become A Member",
                                    style = TextStyle(
                                        fontSize = 16.sp.nonScaledSp,
                                        fontFamily = bold,
                                    ),
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}