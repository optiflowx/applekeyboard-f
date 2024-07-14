package com.optiflowx.optikeysx.app.screens.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.optikeysxPreferences
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.CupertinoSwitch
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.SectionItem

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun InteractionsSection(
    titleTextStyle: TextStyle,
    tileTextStyle: TextStyle,
) {
    val prefs by optikeysxPreferences()
    val isSoundOnKeypress = prefs.isSoundOnKeypress.observeAsState().value
    val isVibrateOnKeypress = prefs.isVibrateOnKeypress.observeAsState().value

    CupertinoSection(
        title = { CupertinoText(stringResource(R.string.interactions), style = titleTextStyle) },
    ) {
        SectionItem(
            title = {
                CupertinoText(
                    text = stringResource(R.string.sound_on_key_press),
                    style = tileTextStyle,
                )
            },
            trailingContent = {
                CupertinoSwitch(

                    checked = isSoundOnKeypress,
                    onCheckedChange = { prefs.isSoundOnKeypress.set(it) }
                )
            }
        )

        SectionItem(
            title = {
                CupertinoText(
                    stringResource(R.string.vibrate_on_key_press), style = tileTextStyle,
                )
            },
            trailingContent = {
                CupertinoSwitch(
                    checked = isVibrateOnKeypress,
                    onCheckedChange = { prefs.isVibrateOnKeypress.set(it) }
                )
            }
        )
    }
}