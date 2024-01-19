package com.optiflowx.applekeyboard.viewmodels

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnection.GET_TEXT_WITH_STYLES
import androidx.compose.runtime.Stable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optiflowx.applekeyboard.databases.dao.FrequentlyUsedEmojiDatabaseDAO
import com.optiflowx.applekeyboard.databases.dbs.FrequentlyUsedDatabase
import com.optiflowx.applekeyboard.databases.entities.FrequentlyUsedEmoji
import com.optiflowx.applekeyboard.languages.english.enListA
import com.optiflowx.applekeyboard.languages.english.enListB
import com.optiflowx.applekeyboard.languages.english.enListC
import com.optiflowx.applekeyboard.languages.english.enListD
import com.optiflowx.applekeyboard.languages.french.frListA
import com.optiflowx.applekeyboard.languages.french.frListB
import com.optiflowx.applekeyboard.languages.french.frListC
import com.optiflowx.applekeyboard.languages.french.frListD
import com.optiflowx.applekeyboard.languages.portuguese.ptListA
import com.optiflowx.applekeyboard.languages.portuguese.ptListB
import com.optiflowx.applekeyboard.languages.portuguese.ptListC
import com.optiflowx.applekeyboard.languages.portuguese.ptListD
import com.optiflowx.applekeyboard.languages.spanish.spListA
import com.optiflowx.applekeyboard.languages.spanish.spListB
import com.optiflowx.applekeyboard.languages.spanish.spListC
import com.optiflowx.applekeyboard.languages.spanish.spListD
import com.optiflowx.applekeyboard.models.Key
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.storage.PreferencesConstants
import com.optiflowx.applekeyboard.storage.PreferencesHelper
import com.optiflowx.applekeyboard.utils.KeyboardLanguage
import com.optiflowx.applekeyboard.utils.KeyboardLocale
import com.optiflowx.applekeyboard.utils.KeyboardType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import splitties.systemservices.vibrator
import java.util.Locale

@Stable
class KeyboardViewModel(context: Context) : ViewModel() {
    //UI
    val isAllCaps = MutableLiveData(false)
    val keyboardType = MutableLiveData(KeyboardType.Normal)
    val isNumberSymbol = MutableLiveData(false)
    val isCapsLock = MutableLiveData(false)
    val isPhoneSymbol = MutableLiveData(false)
    val isEmojiSearch = MutableLiveData(false)

    var soundPool: SoundPool? = null

    //Dictionaries
    val wordsDictionary = MutableLiveData(listOf<String>())

    //Private Variables
    private val englishWords = (enListA + enListB + enListC + enListD).toSet()
    private val spanishWords = (spListA + spListB + spListC + spListD).toSet()
    private val ptWords = (ptListA + ptListB + ptListC + ptListD).toSet()
    private val frenchWords = (frListA + frListB + frListC + frListD).toSet()

    //DAO
    private var fuEmojiDbDAO: FrequentlyUsedEmojiDatabaseDAO

    //Databases
    private var fuEmojiDB: FrequentlyUsedDatabase

    //Database Utils
    var frequentlyUsedEmojis: LiveData<List<FrequentlyUsedEmoji>>

    //UI Locale
    private val keyboardLocale = KeyboardLocale()

    //DataStore
    val preferences = PreferencesHelper(context)
    private val pC = PreferencesConstants

    init {
        //Init Sound Pool
        if (soundPool == null) {
            val audioAttributes =
                AudioAttributes.Builder().setContentType(CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).build()
            soundPool =
                SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(196).build()
        }

        //Init Databases
        fuEmojiDB = FrequentlyUsedDatabase.getInstance(context)

        //Init DAO
        fuEmojiDbDAO = fuEmojiDB.fUsedEmojiDatabaseDAO()

        //Init LiveData
        frequentlyUsedEmojis = fuEmojiDbDAO.getAllEmojis()
    }

    override fun onCleared() {
        super.onCleared()
        soundPool?.release()
        soundPool = null
    }

    fun playSound(soundID: Int?) = viewModelScope.launch(Dispatchers.IO) {
        val value: Boolean = preferences.getStaticPreference(pC.SOUND_ON_KEY_PRESS_KEY, true)
        if (value) {
            soundPool?.play(soundID!!, 0.3f, 0.3f, 1, 0, 1.25f)
        }
    }

    @Suppress("DEPRECATION")
    fun vibrate() = viewModelScope.launch(Dispatchers.IO) {
        val value: Boolean = preferences.getStaticPreference(pC.VIBRATE_ON_KEY_PRESS_KEY, true)
        if (value) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, -1))
            } else vibrator.vibrate(50)
        }
    }

    private fun checkSpelling(context: Context) {
        viewModelScope.launch {
            val value: Boolean = preferences.getLastPreference(pC.AUTO_CHECK_SPELLING_KEY, true)
            if (value) {
                val connection = (context as IMEService).currentInputConnection
                val text = connection.getTextBeforeCursor(1, GET_TEXT_WITH_STYLES)
                if (!text.isNullOrEmpty()) {
                    val lastChar = text.last()
                    if (lastChar == ' ') {
                        val textBeforeCursor =
                            connection.getTextBeforeCursor(24, GET_TEXT_WITH_STYLES)
                        if (textBeforeCursor != null) {
                            val filterText = textBeforeCursor.split(" ").last()
                            val isCorrect = when (preferences.getLastPreference(
                                pC.LOCALE_KEY,
                                KeyboardLanguage.ENGLISH.name
                            )) {
                                KeyboardLanguage.ENGLISH.name -> englishWords.contains(filterText)
                                KeyboardLanguage.SPANISH.name -> spanishWords.contains(filterText)
                                KeyboardLanguage.FRENCH.name -> frenchWords.contains(filterText)
                                KeyboardLanguage.PORTUGUESE.name -> ptWords.contains(filterText)
                                else -> englishWords.contains(filterText)
                            }
                            if (!isCorrect) {
                                connection.deleteSurroundingText(filterText.length, 0)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleCapsLock() = viewModelScope.launch {
        if (isAllCaps.value == true && isCapsLock.value == false) {
            isAllCaps.value = false
        }
    }

    private suspend fun updateCapsLock() {
        val value: Boolean = preferences.getLastPreference(pC.AUTO_CAPITALIZE_KEY, true)
        if (value) {
            isAllCaps.value = true
            isCapsLock.value = false
        }
    }

    private fun handleDoubleSpacePeriod(connection: InputConnection) {
        viewModelScope.launch {
            val value: Boolean = preferences.getLastPreference(pC.DOUBLE_SPACE_PERIOD_KEY, true)
            if (value) {
                connection.deleteSurroundingText(1, 0).let {
                    connection.commitText(". ", ". ".length)
                }
                updateCapsLock()
            }
        }
    }

    private fun handleAutoCapitalizeI(connection: InputConnection) = viewModelScope.launch {
        val value: Boolean = preferences.getLastPreference(pC.AUTO_CAPITALIZE_I_KEY, true)
        if (value) {
            connection.deleteSurroundingText(2, 0).let {
                connection.commitText(" I ", " I ".length)
            }
        }
    }

    private fun getNextSuggestions(connection: InputConnection) {
        val charSequence = connection.getTextBeforeCursor(24, GET_TEXT_WITH_STYLES)
        if (charSequence != null) {
            val filterText = charSequence.split(" ").last()
            viewModelScope.launch {
                preferences.getFlowPreference(pC.LOCALE_KEY, KeyboardLanguage.ENGLISH.name)
                    .collectLatest { language ->
                        wordsDictionary.value = when (language) {
                            KeyboardLanguage.PORTUGUESE.name -> ptWords.filter {
                                it.startsWith(filterText.lowercase())
                            }

                            KeyboardLanguage.SPANISH.name -> spanishWords.filter {
                                it.startsWith(filterText.lowercase())
                            }

                            KeyboardLanguage.FRENCH.name -> frenchWords.filter {
                                it.startsWith(filterText.lowercase())
                            }
                            ///[DEFAULT IS ENGLISH]
                            else -> englishWords.filter {
                                it.startsWith(filterText.lowercase())
                            }
                        }
                    }
            }
        }
    }

    fun onSuggestionClick(suggestion: String, context: Context) {
        val connection = (context as IMEService).currentInputConnection
        val charSequence = connection.getTextBeforeCursor(24, GET_TEXT_WITH_STYLES)

        if (charSequence != null) {
            val textToReplace = charSequence.split(" ").last()
            connection.deleteSurroundingText(textToReplace.length, 0).let {
                if (it) connection.commitText("$suggestion ", suggestion.length + 1)
            }
        }
    }

    fun onEmojiClick(context: Context, emoji: String, title: String) {
        val connection = (context as IMEService).currentInputConnection
        connection.commitText(emoji, emoji.length)

        viewModelScope.launch(Dispatchers.IO) {
            if (!title.lowercase().contains("frequently")) {
                val id = emoji.codePointAt(0)
                val data = fuEmojiDbDAO.getEmojisById(id)
                val all = fuEmojiDbDAO.getAllEmojis().value

                if (data != null && data.emoji == emoji) {
                    fuEmojiDbDAO.delete(data).let { fuEmojiDbDAO.insert(data) }
                } else if (data != null && all != null && all.size == 18) {
                    val last: FrequentlyUsedEmoji = all.last()
                    fuEmojiDbDAO.delete(last).let {
                        fuEmojiDbDAO.insert(FrequentlyUsedEmoji(id = id, emoji = emoji))
                    }
                } else fuEmojiDbDAO.insert(FrequentlyUsedEmoji(id = id, emoji = emoji))
            }
        }
    }

    fun onTKeyClick(key: Key, context: Context, action: String = "return") {
        val connection = (context as IMEService).currentInputConnection

        when (key.id) {
            "ABC" -> keyboardType.value = KeyboardType.Normal

            "123" -> keyboardType.value = KeyboardType.Symbol

            "action" -> {
                if (action == "return") {
                    connection.commitText("\n", "\n".length)
                } else context.sendDefaultEditorAction(false)
            }

            "space" -> {
                val textBeforeCursorA =
                    connection.getTextBeforeCursor(1, GET_TEXT_WITH_STYLES)

                val textBeforeCursorB =
                    connection.getTextBeforeCursor(2, GET_TEXT_WITH_STYLES)

                if (textBeforeCursorA.toString() == " ") {
                    handleDoubleSpacePeriod(connection)
                } else if (textBeforeCursorB.toString() == " i") {
                    handleAutoCapitalizeI(connection)
                } else connection.commitText(" ", " ".length)
            }

            else -> {
                connection.commitText(
                    (if (isAllCaps.value as Boolean) {
                        key.value.uppercase(Locale.getDefault())
                    } else key.value.lowercase(Locale.getDefault())), key.value.length
                )
                getNextSuggestions(connection)
                checkSpelling(context)
                handleCapsLock()
            }
        }
    }

    fun onNumKeyClick(key: Key, context: Context) {
        val connection = (context as IMEService).currentInputConnection
        viewModelScope.launch {
            preferences.getFlowPreference(pC.LOCALE_KEY, KeyboardLanguage.ENGLISH.name)
                .collectLatest {
                    when (key.value) {
                        "*" -> connection.commitText(key.value, key.value.length)
                        "#" -> connection.commitText(key.value, key.value.length)
                        "+" -> connection.commitText(key.value, key.value.length)
                        keyboardLocale.wait(it) -> connection.commitText(";", ";".length)
                        keyboardLocale.pause(it) -> connection.commitText(".", ".".length)
                        else -> connection.commitText(key.id, key.id.length)
                    }
                }
        }
    }

    fun onIKeyClick(key: Key, context: Context) {
        val connection = (context as IMEService).currentInputConnection
        val text = connection.getTextBeforeCursor(16, 0)?.split(" ")?.last()
        val selectedText = connection.getSelectedText(GET_TEXT_WITH_STYLES)

        when (key.id) {
            "erase" -> {
                if (selectedText != null) {
                    connection.commitText("", "".length)
                } else if (text?.codePoints() != null && text.codePoints().toArray()
                        .isNotEmpty()
                ) {
                    connection.deleteSurroundingTextInCodePoints(1, 0)
                } else connection.deleteSurroundingText(1, 0)

                getNextSuggestions(connection)
                checkSpelling(context)
                handleCapsLock()
            }

            "shift" -> {
                if (isCapsLock.value == true) {
                    isCapsLock.value = false
                    isAllCaps.value = false
                } else if (isAllCaps.value!! && !(isCapsLock.value!!)) {
                    isCapsLock.value = true //isAllCaps is already true.
                } else isAllCaps.value = !(isAllCaps.value!!)
            }

            "symbol" -> isNumberSymbol.value = !(isNumberSymbol.value!!)
        }
    }

    fun onABCTap() = viewModelScope.launch {
        keyboardType.value = KeyboardType.Normal
    }

    fun onEmojiTap() = viewModelScope.launch {
        keyboardType.value = KeyboardType.Emoji
    }

    fun onPhoneSymbol() = viewModelScope.launch {
        isPhoneSymbol.value = !(isPhoneSymbol.value!!)
    }
}
