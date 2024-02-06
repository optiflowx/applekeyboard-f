package com.optiflowx.optikeysx.viewmodels

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optiflowx.optikeysx.core.preferences.PreferencesHelper
import com.optiflowx.optikeysx.core.preferences.PrefsConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Immutable
class AppViewModel(context: Context) : ViewModel() {
    val preferences = PreferencesHelper(context)
    private val pC = PrefsConstants
    private val dispatcherIO = Dispatchers.IO

//    init {
//        viewModelScope.launch {
//            val isFirstRun = preferences.getPreference(pC.FIRST_RUN_KEY, true)
//
//            if (isFirstRun) {
//                preferences.putPreference(pC.FIRST_RUN_KEY, false)
//            }
//        }
//    }

    fun updateFontType(value: String) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.FONT_TYPE_KEY, value)
    }

    fun updateVibrateOnKeyPress(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.VIBRATE_ON_KEY_PRESS_KEY, value)
    }

    fun updateSoundOnKeyPress(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.SOUND_ON_KEY_PRESS_KEY, value)
    }

    fun updateAutoCapitalisation(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.AUTO_CAPITALISATION_KEY, value)
    }

    fun updateDotShortcut(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.DOT_SHORTCUT_KEY, value)
    }

    fun updateEnableCapsLock(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.ENABLE_CAPS_LOCK_KEY, value)
    }

    fun updateCheckSpelling(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.CHECK_SPELLING_KEY, value)
    }

    fun updateCharacterPreview(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.CHARACTER_PREVIEW_KEY, value)
    }

    fun updateAutoCorrection(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.AUTO_CORRECTION_KEY, value)
    }

    fun updateSmartPunctuation(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.SMART_PUNCTUATION_KEY, value)
    }

    fun updatePredictive(value: Boolean) = viewModelScope.launch(dispatcherIO) {
        preferences.putPreference(pC.PREDICTIVE_KEY, value)
    }
}