package com.optiflowx.applekeyboard.composables.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.ui.defaultFontFamily
import io.github.alexzhirkevich.cupertino.Surface

@Composable
fun ImportantNotice() {
    val radius = 12.dp
    Surface(
        shape = RoundedCornerShape(radius),
        color = Color.Red.copy(alpha = 0.25f),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .border(BorderStroke(1.dp, Color.Red), RoundedCornerShape(radius))
    ) {
        Text(
            "IMPORTANT MESSAGE:\n" +
                    "\n" +
                    "Thank you for choosing our application with the application ID: `com.optiflowx.applekeyboard`. " +
                    "We would like to reassure you that we DO NOT collect any personal information from our users. " +
                    "This application is a paid product because it is developed solely by OptiFlowX. Your purchase is a valuable " +
                    "expression of support for the dedication and effort put into creating and maintaining this app.\n" +
                    "\n" +
                    "PLEASE BE CAUTIOUS: acquiring this application for free from unofficial sources may expose " +
                    "you to potential malware threats. We strongly advise against accepting or using any " +
                    "free or modified versions of this specific app.\n" +
                    "\n" +
                    "We sincerely appreciate your support and understanding. If you have any concerns or questions, " +
                    "feel free to contact us. Thank you for being a part of the OptiFlowX community! \uD83D\uDE0A",
            style = TextStyle(
                color = Color.Red,
                fontSize = TextUnit(12f, TextUnitType.Sp),
                fontFamily = defaultFontFamily,
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}