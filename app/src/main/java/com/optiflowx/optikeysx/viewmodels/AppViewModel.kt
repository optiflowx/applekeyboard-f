package com.optiflowx.optikeysx.viewmodels

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optiflowx.optikeysx.core.preferences.PreferencesHelper
import com.optiflowx.optikeysx.core.preferences.PrefsConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Immutable
class AppViewModel(context: Context) : ViewModel() {
    @Stable
    val preferences = PreferencesHelper(context)

    @Stable
    private val pC = PrefsConstants

    @Stable
    private val dispatcherIO = Dispatchers.IO

    init {
        viewModelScope.launch {
            val isFirstRun = preferences.getPreference(pC.FIRST_RUN_KEY, true)

            if (isFirstRun) {
                preferences.putPreference(pC.FIRST_RUN_KEY, false)
            }
        }
    }

    @Stable
    fun updateLocale(value: String) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.LOCALE_KEY, value)
    }

    @Stable
    fun updateFontType(value: String) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.FONT_TYPE_KEY, value)
    }

    @Stable
    fun updateVibrateOnKeyPress(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.VIBRATE_ON_KEY_PRESS_KEY, value)
    }

    @Stable
    fun updateSoundOnKeyPress(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.SOUND_ON_KEY_PRESS_KEY, value)
    }

    @Stable
    fun updateAutoCapitalisation(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.AUTO_CAPITALISATION_KEY, value)
    }

    @Stable
    fun updateDotShortcut(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.DOT_SHORTCUT_KEY, value)
    }

    @Stable
    fun updateEnableCapsLock(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.ENABLE_CAPS_LOCK_KEY, value)
    }

    @Stable
    fun updateCheckSpelling(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.CHECK_SPELLING_KEY, value)
    }

    @Stable
    fun updateCharacterPreview(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.CHARACTER_PREVIEW_KEY, value)
    }

    @Stable
    fun updateAutoCorrection(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.AUTO_CORRECTION_KEY, value)
    }

    @Stable
    fun updateSmartPunctuation(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.SMART_PUNCTUATION_KEY, value)
    }

    @Stable
    fun updatePredictive(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.PREDICTIVE_KEY, value)
    }
}