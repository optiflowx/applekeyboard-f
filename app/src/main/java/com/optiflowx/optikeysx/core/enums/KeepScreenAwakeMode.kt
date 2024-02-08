package com.optiflowx.optikeysx.core.enums

import androidx.compose.runtime.Composable
import dev.patrickgold.jetpref.datastore.ui.listPrefEntries

enum class KeepScreenAwakeMode {
    NEVER, WHEN_LISTENING, WHEN_OPEN;

    companion object {
        @Composable
        fun listEntries() = listPrefEntries {
            entry(
                key = NEVER,
                label = "Never",
            )
            entry(
                key = WHEN_LISTENING,
                label = "When Listening",
            )
            entry(
                key = WHEN_OPEN,
                label = "When Open",
            )
        }
    }
}