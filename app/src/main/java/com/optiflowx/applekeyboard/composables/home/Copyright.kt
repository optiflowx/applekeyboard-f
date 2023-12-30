package com.optiflowx.applekeyboard.composables.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.ui.cupertinoBlue1
import com.optiflowx.applekeyboard.ui.defaultFontFamily
import io.github.alexzhirkevich.cupertino.Surface

@Composable
fun CopyrightView() {
    val radius = 12.dp
    Surface(
        shape = RoundedCornerShape(radius),
        color = cupertinoBlue1.copy(alpha = 0.25f),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 5.dp)
            .border(BorderStroke(1.dp, cupertinoBlue1), RoundedCornerShape(radius))
    ) {
        Text(
            "The keyboard design and certain associated resources within this application, " +
                    "identified by the application ID `com.optiflowx.applekeyboard`, are based on or inspired by " +
                    "the intellectual property of Apple Inc. All rights related to the design, layout, and " +
                    "specific resources that mimic Apple's design are expressly reserved by Apple Inc.\n" +
                    "\n" +
                    "This application, developed by OptiFlowX, is designed for use on Android devices. " +
                    "While OptiFlowX holds the rights to the application as a whole, it is important to " +
                    "acknowledge that the keyboard design and specific resources incorporated herein " +
                    "draw inspiration from Apple Inc.'s intellectual property.\n" +
                    "\n" +
                    "Any unauthorized reproduction, distribution, or modification of the application design and " +
                    "associated resources that mimic Apple's design may infringe upon the intellectual property rights. " +
                    "This application is fully owned by OptiFlowX, and no other person holds responsibility for this app. " +
                    "OptiFlowX operates under fair use and acknowledges Apple Inc. rights to its design property.\n" +
                    "\n" +
                    "2023 © OptiFlowX. All rights reserved. This application is not " +
                    "endorsed by or affiliated with Apple Inc.",
            style = TextStyle(
                color = cupertinoBlue1,
                fontSize = TextUnit(14f, TextUnitType.Sp),
                fontFamily = defaultFontFamily,
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}