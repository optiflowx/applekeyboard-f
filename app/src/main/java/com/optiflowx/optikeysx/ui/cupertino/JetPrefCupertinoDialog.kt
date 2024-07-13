package com.optiflowx.optikeysx.ui.cupertino

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import io.github.alexzhirkevich.cupertino.AlertActionStyle
import io.github.alexzhirkevich.cupertino.CupertinoAlertDialog
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun JetPrefCupertinoAlertDialog(
    title: String,
    confirmLabel: String = "Confirm",
    onConfirm: () -> Unit = { },
    dismissLabel: String = "Cancel",
    onDismiss: () -> Unit = { },
    allowOutsideDismissal: Boolean = true,
    onOutsideDismissal: () -> Unit = onDismiss,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
    message: @Composable () -> Unit,
) {
    CupertinoAlertDialog(
        title = { CupertinoText(text = title) },
        message = message,
        onDismissRequest = { if (allowOutsideDismissal) onOutsideDismissal() },
        properties = properties,
        buttons = {

            this.action(
                title = { CupertinoText(text = dismissLabel) },
                style = AlertActionStyle.Destructive,
                onClick = onDismiss,
            )
            this.action(
                title = { CupertinoText(text = confirmLabel) },
                style = AlertActionStyle.Default,
                onClick = onConfirm,
            )
        }
    )
}