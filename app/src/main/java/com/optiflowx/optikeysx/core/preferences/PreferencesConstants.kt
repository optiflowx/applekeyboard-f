package com.optiflowx.optikeysx.core.preferences

import androidx.compose.runtime.Immutable
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

@Immutable
object PrefsConstants {
    val FIRST_RUN_KEY = booleanPreferencesKey("FIRST_RUN_KEY")
    val LOCALE_KEY = stringPreferencesKey("LOCALE_KEY")
    val FONT_TYPE_KEY = stringPreferencesKey("FONT_TYPE_KEY")
    val VIBRATE_ON_KEY_PRESS_KEY = booleanPreferencesKey("VIBRATE_ON_KEY_PRESS_KEY")
    val SOUND_ON_KEY_PRESS_KEY = booleanPreferencesKey("SOUND_ON_KEY_PRESS_KEY")
    val AUTO_CAPITALISATION_KEY = booleanPreferencesKey("AUTO_CAPITALISATION_KEY")
    val AUTO_CORRECTION_KEY = booleanPreferencesKey("AUTO_CORRECTION_KEY")
    val DOT_SHORTCUT_KEY = booleanPreferencesKey("DOT_SHORTCUT_KEY")
    val ENABLE_CAPS_LOCK_KEY = booleanPreferencesKey("ENABLE_CAPS_LOCK_KEY")
    val PREDICTIVE_KEY = booleanPreferencesKey("PREDICTIVE_KEY")
    val SMART_PUNCTUATION_KEY = booleanPreferencesKey("SMART_PUNCTUATION_KEY")
    val CHARACTER_PREVIEW_KEY = booleanPreferencesKey("CHARACTER_PREVIEW_KEY")
    val CHECK_SPELLING_KEY = booleanPreferencesKey("CHECK_SPELLING_KEY")
}