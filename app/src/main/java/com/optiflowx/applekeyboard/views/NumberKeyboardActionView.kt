package com.optiflowx.applekeyboard.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.ui.cupertinoBlue1
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.ChevronDown
import io.github.alexzhirkevich.cupertino.icons.outlined.ChevronUp

@Composable
fun NumberKeyboardActionView(viewModel: KeyboardViewModel) {
    val imeService = LocalContext.current as IMEService

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
                modifier = Modifier.clickable(
                    onClick = { }
                ),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                CupertinoIcons.Default.ChevronDown, "arrowDown",
                modifier = Modifier.clickable(
                    onClick = {  }
                ),
            )
        }
        Text(
            viewModel.actionDone.observeAsState().value!!,
            color = cupertinoBlue1,
            modifier = Modifier.clickable(
                onClick = {
                    imeService.currentInputConnection?.performEditorAction(6)
                }
            )
        )
    }
}