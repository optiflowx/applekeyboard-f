package com.optiflowx.applekeyboard.viewmodels

import android.content.Context
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optiflowx.applekeyboard.storage.PreferencesConstants
import com.optiflowx.applekeyboard.storage.PreferencesHelper
import kotlinx.coroutines.launch


@Stable
class AppViewModel(context: Context) : ViewModel() {
    val preferences = PreferencesHelper(context)
    private val pC = PreferencesConstants

    init {
        viewModelScope.launch {
            val isFirstRun = preferences.getStaticPreference(pC.FIRST_RUN_KEY, true)

            if (isFirstRun) {
                preferences.putPreference(pC.FIRST_RUN_KEY, false)
            }
        }
    }

    fun updateLocale(value: String) = viewModelScope.launch {
        preferences.putPreference(pC.LOCALE_KEY, value)
    }

    fun updateFontType(value: String) = viewModelScope.launch {
        preferences.putPreference(pC.FONT_TYPE_KEY, value)
    }

    fun updateVibrateOnKeyPress(value: Boolean) = viewModelScope.launch {
        preferences.putPreference(pC.VIBRATE_ON_KEY_PRESS_KEY, value)
    }

    fun updateSoundOnKeyPress(value: Boolean) = viewModelScope.launch {
        preferences.putPreference(pC.SOUND_ON_KEY_PRESS_KEY, value)
    }

    fun updateAutoCapitalize(value: Boolean) = viewModelScope.launch {
        preferences.putPreference(pC.AUTO_CAPITALIZE_KEY, value)
    }

    fun updateAutoCorrect(value: Boolean) = viewModelScope.launch {
        preferences.putPreference(pC.AUTO_CORRECT_KEY, value)
    }

    fun updateDoubleSpacePeriod(value: Boolean) = viewModelScope.launch {
        preferences.putPreference(pC.DOUBLE_SPACE_PERIOD_KEY, value)
    }

    fun updateAutoCapitalizeI(value: Boolean) = viewModelScope.launch {
        preferences.putPreference(pC.AUTO_CAPITALIZE_I_KEY, value)
    }

    fun updateShowSuggestions(value: Boolean) = viewModelScope.launch {
        preferences.putPreference(pC.SHOW_SUGGESTIONS_KEY, value)
    }

    fun updateAutoCheckSpelling(value: Boolean) = viewModelScope.launch {
        preferences.putPreference(pC.AUTO_CHECK_SPELLING_KEY, value)
    }
}