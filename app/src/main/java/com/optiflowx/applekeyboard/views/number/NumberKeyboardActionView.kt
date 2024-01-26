package com.optiflowx.applekeyboard.views.number

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.core.services.IMEService
import com.optiflowx.applekeyboard.core.utils.KeyboardLocale
import com.optiflowx.applekeyboard.utils.appFontType
import com.optiflowx.applekeyboard.utils.nonScaledSp
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.ChevronDown
import io.github.alexzhirkevich.cupertino.icons.outlined.ChevronUp
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue

@Composable
fun NumberKeyboardActionView(locale: String) {
    val imeService = LocalContext.current as IMEService
    val keyboardLocale = KeyboardLocale()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                CupertinoIcons.Default.ChevronUp, "arrowUp",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(22.dp)
                    .clickable(
                        onClick = { }
                    ),
            )
            Spacer(modifier = Modifier.width(15.dp))
            Icon(
                CupertinoIcons.Default.ChevronDown, "arrowDown",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(22.dp)
                    .clickable(
                        onClick = { imeService.currentInputConnection?.performEditorAction(6) }
                    ),
            )
        }
        Text(
            keyboardLocale.action("done", locale).replaceFirstChar {
                it.uppercase()
            },
            style = TextStyle(
                fontFamily = appFontType("Bold"),
                color = CupertinoColors.systemBlue,
                fontSize = TextUnit(17f, TextUnitType.Sp).nonScaledSp,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            modifier = Modifier.clickable(
                onClick = {
                    imeService.currentInputConnection?.performEditorAction(6)
                }
            )
        )
    }
}