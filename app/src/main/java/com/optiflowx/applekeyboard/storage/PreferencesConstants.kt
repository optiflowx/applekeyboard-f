package com.optiflowx.applekeyboard.storage

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesConstants {
    val FIRST_RUN_KEY = booleanPreferencesKey("FIRST_RUN_KEY")
    val LOCALE_KEY = stringPreferencesKey("LOCALE_KEY")
    val FONT_TYPE_KEY = stringPreferencesKey("FONT_TYPE_KEY")
    val VIBRATE_ON_KEY_PRESS_KEY = booleanPreferencesKey("VIBRATE_ON_KEY_PRESS_KEY")
    val SOUND_ON_KEY_PRESS_KEY = booleanPreferencesKey("SOUND_ON_KEY_PRESS_KEY")
    val AUTO_CAPITALIZE_KEY = booleanPreferencesKey("AUTO_CAPITALIZE_KEY")
    val AUTO_CORRECT_KEY = booleanPreferencesKey("AUTO_CORRECT_KEY")
    val DOUBLE_SPACE_PERIOD_KEY = booleanPreferencesKey("DOUBLE_SPACE_PERIOD_KEY")
    val AUTO_CAPITALIZE_I_KEY = booleanPreferencesKey("AUTO_CAPITALIZE_I_KEY")
    val SHOW_SUGGESTIONS_KEY = booleanPreferencesKey("SHOW_SUGGESTIONS_KEY")
    val AUTO_CHECK_SPELLING_KEY = booleanPreferencesKey("AUTO_CHECK_SPELLING_KEY")
}