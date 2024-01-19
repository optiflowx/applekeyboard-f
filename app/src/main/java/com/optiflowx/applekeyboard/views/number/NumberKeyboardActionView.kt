package com.optiflowx.applekeyboard.views.number

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.storage.PreferencesConstants
import com.optiflowx.applekeyboard.ui.cupertinoBlue1
import com.optiflowx.applekeyboard.utils.KeyboardLocale
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.ChevronDown
import io.github.alexzhirkevich.cupertino.icons.outlined.ChevronUp

@Composable
fun NumberKeyboardActionView(viewModel: KeyboardViewModel) {
    val imeService = LocalContext.current as IMEService
    val keyboardLocale = KeyboardLocale()

    var locale by rememberSaveable { mutableStateOf("ENGLISH") }

    LaunchedEffect(viewModel.preferences) {
        locale = viewModel.preferences.getStaticPreference(PreferencesConstants.LOCALE_KEY, "ENGLISH")
    }

    val text by rememberSaveable {
        mutableStateOf(keyboardLocale.action("done", locale))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(40.dp)
    ) {
        Row {
            Icon(
                CupertinoIcons.Default.ChevronUp, "arrowUp",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    onClick = { }
                ),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                CupertinoIcons.Default.ChevronDown, "arrowDown",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    onClick = {  }
                ),
            )
        }
        Text(
            text,
            color = cupertinoBlue1,
            modifier = Modifier.clickable(
                onClick = {
                    imeService.currentInputConnection?.performEditorAction(6)
                }
            )
        )
    }
}