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
import com.optiflowx.applekeyboard.ui.regular
import io.github.alexzhirkevich.cupertino.Surface

@Composable
fun ImportantNotice() {
    val radius = 12.dp
    Surface(
        shape = RoundedCornerShape(radius),
        color = Color.DarkGray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(radius))
    ) {
        Text(
            "IMPORTANT MESSAGE: I would like to reassure you that I do not collect any personal information from any user. " +
                    "This application is a PAID product because it is developed solely by OptiFlowX. Your purchase is a valuable " +
                    "expression of support for the dedication and effort put into creating and maintaining this app.\n" +
                    "\n" +
                    "CAUTION: Acquiring this application for free from unofficial sources may expose " +
                    "you to potential malware threats. Thus, I strongly advise against accepting or using any " +
                    "modified versions of this app.\n" +
                    "\n" +
                    "Thank you for your support and understanding. If you have any concerns or questions, " +
                    "feel free to contact me.\uD83D\uDE0A",
            style = TextStyle(
                color = Color.White,
                fontSize = TextUnit(12f, TextUnitType.Sp),
                fontFamily = regular,
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}