package com.optiflowx.optikeysx.ui.cupertino

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ui.regular
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetContent
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoButtonDefaults
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun CopyrightBottomSheet(
    scaffoldState : CupertinoBottomSheetScaffoldState,
    sheetSectionColor : Color
) {

    val coroutineScope = rememberCoroutineScope()

    CupertinoBottomSheetContent(
        topBar = {
            CupertinoTopAppBar(
                title = {
                    CupertinoText("COPYRIGHT NOTICE")
                },
                actions = {
                    CupertinoButton(
                        colors = CupertinoButtonDefaults.borderlessButtonColors(),
                        onClick = {
                            coroutineScope.launch {
                                scaffoldState.bottomSheetState.hide()
                            }
                        }
                    ) {
                        CupertinoText("Done")
                    }
                },
                isTransparent = true
            )
        }
    ) { pv ->
        CupertinoSection(
            modifier = Modifier.padding(pv),
            containerColor = Color.Transparent,
            color = sheetSectionColor
        ) {
            item {
                CupertinoText(
                    "The keyboard design and certain associated resources within this application, " +
                            "identified by the application ID `com.optiflowx.optikeysx`, are based on or inspired by " +
                            "the intellectual property of Apple Inc. All rights related to the design, layout, and " +
                            "specific resources that mimic Apple's design are expressly reserved by Apple Inc.\n" +
                            "\n" +
                            "This application, developed by OptiFlowX, is designed for use on Android devices. " +
                            "While OptiFlowX holds the rights to the application as a whole, it is important to " +
                            "acknowledge that any unauthorized reproduction, distribution, or modification of the application " +
                            "design and associated resources may infringe upon the intellectual property rights. " +
                            "This application is fully owned by OptiFlowX, and no other person holds responsibility for this app. " +
                            "OptiFlowX operates under fair use and acknowledges Apple Inc. rights to its design property.\n" +
                            "\n" +
                            "2024 © OptiFlowX. All rights reserved. This application is not " +
                            "endorsed by or affiliated with Apple Inc.",
                    style = TextStyle(
                        color = CupertinoColors.systemBlue,
                        fontSize = TextUnit(14f, TextUnitType.Sp).nonScaledSp,
                        fontFamily = regular,
                    ),
                    modifier = Modifier.padding(it)
                )
            }
        }
    }
}

