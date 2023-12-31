package com.optiflowx.applekeyboard.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.optiflowx.applekeyboard.utils.booleanLiveData
import com.optiflowx.applekeyboard.utils.intLiveData
import com.optiflowx.applekeyboard.utils.stringLiveData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Stable
class AppViewModel(context: Context) : ViewModel() {
    //SharedPrefs Data`
    private val prefsFileName = "com.optiflowx.applekeyboard"
    private val prefsMode = Context.MODE_PRIVATE
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFileName, prefsMode)

    private val isFirstRun = prefs.booleanLiveData("isFirstRun", true).asFlow()
    val locale = prefs.stringLiveData("locale", "en")
    val fontType = prefs.stringLiveData("fontType", "regular")
    val fontSize = prefs.intLiveData("fontSize", 18)
    val vibrateOnKeyPress = prefs.booleanLiveData("vibrateOnKeyPress", true)
    val soundOnKeyPress = prefs.booleanLiveData("soundOnKeyPress", true)
    val autoCapitalize = prefs.booleanLiveData("autoCapitalize", true)
    val autoCorrect = prefs.booleanLiveData("autoCorrect", false)
    val doubleSpacePeriod = prefs.booleanLiveData("doubleSpacePeriod", true)
    val autoCapitalizeFirstWord = prefs.booleanLiveData("autoCapitalizeFirstWord", false)
    val autoCapitalizeI = prefs.booleanLiveData("autoCapitalizeI", true)
    val showSuggestions = prefs.booleanLiveData("showSuggestions", true)
    val showEmojiSearchBar = prefs.booleanLiveData("showEmojiSearchBar", true)
    val autoCheckSpelling = prefs.booleanLiveData("autoCheckSpelling", false)

    init {
        // Set isFirstRun to false
        viewModelScope.launch {
            isFirstRun.collectLatest {
                if (it) prefs.edit().putBoolean("isFirstRun", false).apply()
                Log.d("AppViewModel", "postUpdateIsFirstRun: $it")
            }
        }

    }

    fun updateStringColumn(columnInfo: String, value: String) {
        prefs.edit().putString(columnInfo, value).apply()
    }

    fun updateBooleanColumn(columnInfo: String, value: Boolean) {
        prefs.edit().putBoolean(columnInfo, value).apply()
    }
}