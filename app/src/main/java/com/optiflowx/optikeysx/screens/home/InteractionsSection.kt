package com.optiflowx.optikeysx.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import com.optiflowx.optikeysx.optikeysxPreferences
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.switch

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun InteractionsSection(
    titleTextStyle: TextStyle,
    tileTextStyle: TextStyle,
) {
    val prefs by optikeysxPreferences()
    val isSoundOnKeypress = prefs.isSoundOnKeypress.observeAsState().value
    val isVibrateOnKeypress = prefs.isVibrateOnKeypress.observeAsState().value
    val isAutoSwitchBackToNormal = prefs.autoSwitchIBackIME.observeAsState().value

    CupertinoSection(
        title = { CupertinoText("INTERACTIONS", style = titleTextStyle) },
    ) {
        this.switch(
            title = {
                CupertinoText(
                    text = "Sound On Key Press",
                    style = tileTextStyle,
                )
            },
            checked = isSoundOnKeypress,
            onCheckedChange = { prefs.isSoundOnKeypress.set(it) }
        )
        this.switch(
            title = {
                CupertinoText(
                    "Vibrate On Key Press", style = tileTextStyle,
                )
            },
            checked = isVibrateOnKeypress,
            onCheckedChange = { prefs.isVibrateOnKeypress.set(it) }
        )
        switch(
            title = {
                CupertinoText(
                    "Auto Switch Back To Normal", style = tileTextStyle,
                )
            },
            checked = isAutoSwitchBackToNormal,
            onCheckedChange = { prefs.autoSwitchIBackIME.set(it) }
        )
    }
}