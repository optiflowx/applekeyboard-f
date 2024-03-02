package com.optiflowx.optikeysx.ui.cupertino

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.github.alexzhirkevich.cupertino.Surface

@Composable
fun JetPrefCupertinoAlertDialog(
    title: String,
    modifier: Modifier = Modifier,
    confirmLabel: String? = null,
    onConfirm: () -> Unit = { },
    dismissLabel: String? = null,
    onDismiss: () -> Unit = { },
    neutralLabel: String? = null,
    onNeutral: () -> Unit = { },
    allowOutsideDismissal: Boolean = true,
    onOutsideDismissal: () -> Unit = onDismiss,
    trailingIconTitle: @Composable () -> Unit = { },
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
    scrollModifier: Modifier = Modifier.verticalScroll(rememberScrollState()),
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    contentPadding: PaddingValues = JetPrefAlertDialogDefaults.ContentPadding,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = { if (allowOutsideDismissal) onOutsideDismissal() },
        properties = properties,
    ) {
        Surface(
            modifier = modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .widthIn(max = JetPrefAlertDialogDefaults.MaxDialogWidth),
            shape = shape,
            color = backgroundColor,
            contentColor = contentColor,
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .height(64.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(1.0f),
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    trailingIconTitle()
                }
                Box(
                    modifier = Modifier
                        .padding(contentPadding)
                        .weight(1.0f, fill = false)
                        .fillMaxWidth()
                        .then(scrollModifier),
                ) {
                    content()
                }
                Row(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
                    if (neutralLabel != null && neutralLabel.isNotBlank()) {
                        TextButton(
                            onClick = onNeutral,
                            modifier = Modifier.padding(end = 8.dp),
                        ) {
                            Text(neutralLabel)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1.0f))
                    if (!dismissLabel.isNullOrBlank()) {
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.padding(end = 8.dp),
                        ) {
                            Text(dismissLabel)
                        }
                    }
                    if (!confirmLabel.isNullOrBlank()) {
                        TextButton(
                            onClick = onConfirm,
                        ) {
                            Text(confirmLabel)
                        }
                    }
                }
            }
        }
    }
}

object JetPrefAlertDialogDefaults {
    /**
     * The default content padding for [JetPrefAlertDialog].
     */
    val ContentPadding = PaddingValues(horizontal = 24.dp)

    /**
     * The maximum dialog width for [JetPrefAlertDialog].
     */
    val MaxDialogWidth = 320.dp
}