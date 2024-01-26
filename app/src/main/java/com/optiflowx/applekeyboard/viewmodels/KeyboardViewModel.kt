package com.optiflowx.applekeyboard.viewmodels

import android.content.ClipboardManager
import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.util.Log
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnection.GET_TEXT_WITH_STYLES
import androidx.compose.runtime.Stable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optiflowx.applekeyboard.core.algorithm.DictationProcessor
import com.optiflowx.applekeyboard.core.data.Key
import com.optiflowx.applekeyboard.core.database.dao.ClipboardDatabaseDAO
import com.optiflowx.applekeyboard.core.database.dao.FrequentlyUsedEmojiDatabaseDAO
import com.optiflowx.applekeyboard.core.database.dbs.ClipboardDatabase
import com.optiflowx.applekeyboard.core.database.dbs.FrequentlyUsedDatabase
import com.optiflowx.applekeyboard.core.database.entities.ClipData
import com.optiflowx.applekeyboard.core.database.entities.FrequentlyUsedEmoji
import com.optiflowx.applekeyboard.core.enums.KeyboardLanguage
import com.optiflowx.applekeyboard.core.enums.KeyboardType
import com.optiflowx.applekeyboard.core.preferences.PreferencesConstants
import com.optiflowx.applekeyboard.core.preferences.PreferencesHelper
import com.optiflowx.applekeyboard.core.services.IMEService
import com.optiflowx.applekeyboard.core.utils.KeyboardLocale
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
    val isShowOptions = MutableLiveData(false)

    //Dictionaries
    val wordsDictionary = MutableLiveData(listOf<String>())

    //Private Variables
    private val englishWords = (enListA + enListB + enListC + enListD).toSet()
    private val spanishWords = (spListA + spListB + spListC + spListD).toSet()
    private val ptWords = (ptListA + ptListB + ptListC + ptListD).toSet()
    private val frenchWords = (frListA + frListB + frListC + frListD).toSet()

    //Sound
    lateinit var soundPool: SoundPool

    //DAO
    private lateinit var fuEmojiDbDAO: FrequentlyUsedEmojiDatabaseDAO
    private lateinit var clipDataDbDAO: ClipboardDatabaseDAO

    //Databases
    private lateinit var fuEmojiDB: FrequentlyUsedDatabase
    private lateinit var clipboardDB: ClipboardDatabase

    //Database Utils
    lateinit var frequentlyUsedEmojis: LiveData<List<FrequentlyUsedEmoji>>
    lateinit var clipData: LiveData<List<ClipData>>

    //DictationProcessor
    private lateinit var dictationProcessor: DictationProcessor
    private lateinit var dictionary: Set<String>

    //UI Locale
    private val keyboardLocale = KeyboardLocale()
    private val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    //DataStore
    val preferences = PreferencesHelper(context)
    private val pC = PreferencesConstants

    init {
        viewModelScope.launch(Dispatchers.IO) {
            initSoundPool()
            //Init Databases
            fuEmojiDB = FrequentlyUsedDatabase.getInstance(context)
            clipboardDB = ClipboardDatabase.getInstance(context)

            //Init DAO
            fuEmojiDbDAO = fuEmojiDB.fUsedEmojiDatabaseDAO()
            clipDataDbDAO = clipboardDB.clipboardDAO()

            //Init LiveData
            frequentlyUsedEmojis = fuEmojiDbDAO.getAllEmojis()
            clipData = clipDataDbDAO.getAllClipData()
        }

        viewModelScope.launch(Dispatchers.IO) {
            clipboardManager.addPrimaryClipChangedListener {
                val clipData = clipboardManager.primaryClip?.getItemAt(0)?.text
                Log.d("KeyboardViewModel", "Clipboard: $clipData")
                viewModelScope.launch(Dispatchers.IO) {
                    if (!clipData.isNullOrEmpty()) {
                        val text = clipData.toString()
                        val id = text.hashCode()

                        clipDataDbDAO.insert(ClipData(id, text))
                    }
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            preferences.getFlowPreference(pC.LOCALE_KEY, "English")
                .collect { locale ->
                    dictionary = when (locale) {
                        "French" -> frenchWords
                        "Spanish" -> spanishWords
                        "Portuguese" -> ptWords
                        else -> englishWords
                    }

                    dictationProcessor = DictationProcessor(dictionary)
                }
        }
    }

    override fun onCleared() {
        super.onCleared()

        fuEmojiDB.close()
        clipboardDB.close()
        soundPool.release()

        clipboardManager.removePrimaryClipChangedListener {}
    }

    @Stable
    fun playSound(soundID: Int) = viewModelScope.launch(Dispatchers.IO) {
//        val value: Boolean = preferences.getPreference(pC.SOUND_ON_KEY_PRESS_KEY, true)
//        if (value) {
//            soundPool.play(soundID!!, 0.3f, 0.3f, 1, 0, 1.25f)
//        }
        soundPool.play(soundID, 0.3f, 0.3f, 1, 0, 1.25f)
    }

    private fun initSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(42)
            .setAudioAttributes(audioAttributes)
            .build()
    }

    @Stable
    fun pasteTextFromClipboard(text: String, context: Context) {
        val connection = (context as IMEService).currentInputConnection
        connection.commitText(text, text.length)
    }

    @Stable
    fun clearClipboard(data: List<ClipData>?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!data.isNullOrEmpty()) {
                clipDataDbDAO.clear(data)
            }
        }
    }

    @Stable
    fun vibrate() {
        viewModelScope.launch(Dispatchers.IO) {
            val value: Boolean = preferences.getPreference(pC.VIBRATE_ON_KEY_PRESS_KEY, true)
            if (value) {
                @Suppress("DEPRECATION")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, -1))
                } else vibrator.vibrate(50)
            }
        }
    }

    @Stable
    private fun checkSpelling(connection: InputConnection) {
        viewModelScope.launch(Dispatchers.IO) {
            val checkSpelling: Boolean = preferences.getPreference(pC.CHECK_SPELLING_KEY, true)
            val autoCorrect = preferences.getPreference(pC.AUTO_CORRECTION_KEY, true)
            //Remove the space first
            if (checkSpelling && autoCorrect) {
                connection.deleteSurroundingText(1, 0).let {
                    val tBC = connection.getTextBeforeCursor(12, GET_TEXT_WITH_STYLES)
                    if (tBC != null) {
                        val lastWord = tBC.split(" ").last()
                        Log.d("KeyboardDictation", "Last Word: $lastWord")

                        val correction = dictationProcessor.correct(lastWord)

                        connection.deleteSurroundingText(lastWord.length, 0).let { value ->
                            if (value) connection.commitText("$correction ", 0)
                        }
                    }
                }
            }
        }
    }

    @Stable
    private fun handleCapsLock() {
        if (isAllCaps.value == true && isCapsLock.value == false) {
            isAllCaps.value = false
        }
    }

    @Stable
    private suspend fun updateCapsLock() {
        val value: Boolean = preferences.getPreference(pC.AUTO_CAPITALISATION_KEY, true)
        if (value) {
            isAllCaps.value = true
            isCapsLock.value = false
        }
    }

    @Stable
    private fun handleDotShortcut(connection: InputConnection) {
        viewModelScope.launch {
            val value: Boolean = preferences.getPreference(pC.DOT_SHORTCUT_KEY, true)
            if (value) {
                connection.deleteSurroundingText(1, 0).let {
                    connection.commitText(". ", ". ".length)
                }
                updateCapsLock()
            }
        }
    }

    @Stable
    private fun capitalizeI(connection: InputConnection) {
        viewModelScope.launch {
            val value: Boolean = preferences.getPreference(pC.AUTO_CAPITALISATION_KEY, true)
            if (value) {
                connection.deleteSurroundingText(2, 0).let {
                    connection.commitText(" I ", " I ".length)
                }
            }
        }
    }

    @Stable
    private fun getNextSuggestions(connection: InputConnection) {
        val charSequence = connection.getTextBeforeCursor(24, GET_TEXT_WITH_STYLES)
        if (charSequence != null) {
            val filterText = charSequence.split(" ").last()
            wordsDictionary.value = dictionary.filter {
                it.startsWith(filterText.lowercase())
            }
        }
    }

    @Stable
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

    @Stable
    fun onEmojiClick(context: Context, emoji: String, title: String) {
        val connection = (context as IMEService).currentInputConnection
        connection.commitText(emoji, emoji.length)

        viewModelScope.launch(Dispatchers.IO) {
            if (!title.lowercase().contains("frequently")) {
                val id = emoji.hashCode()
                val data = fuEmojiDbDAO.getEmojisById(id)
                val all = fuEmojiDbDAO.getAllEmojis().value

                if (data != null && data.emoji == emoji) {
                    fuEmojiDbDAO.deleteAndInsertEmoji(data, data)
                } else if (data != null && all != null && all.size == 18) {
                    val oldData = all.last()
                    val newData = FrequentlyUsedEmoji(id, emoji)

                    fuEmojiDbDAO.deleteAndInsertEmoji(newData, oldData)
                } else fuEmojiDbDAO.insert(FrequentlyUsedEmoji(id = id, emoji = emoji))
            }
        }
    }

    @Stable
    fun onTKeyClick(key: Key, context: Context, action: String = "return") {
        val connection = (context as IMEService).currentInputConnection

        when (key.id) {
            "ABC" -> onABCTap()

            "123" -> on123Tap()

            "action" -> onAction(connection, context, action)

            "space" -> onSpace(connection)

            else -> onText(connection, key)
        }
    }

    @Stable
    fun onNumKeyClick(key: Key, context: Context) {
        val connection = (context as IMEService).currentInputConnection
        viewModelScope.launch(Dispatchers.IO) {
            preferences.getFlowPreference(pC.LOCALE_KEY, KeyboardLanguage.English.name)
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

    @Stable
    fun onIKeyClick(key: Key, context: Context) {
        when (key.id) {
            "emoji" -> onEmoji()

            "erase" -> onErase(context)

            "shift" -> onShift()

            "symbol" -> onNumSymbol()
        }
    }

    @Stable
    fun onABCTap() {
        keyboardType.value = KeyboardType.Normal
    }

    @Stable
    fun onPhoneSymbol() {
        isPhoneSymbol.value = !(isPhoneSymbol.value!!)
    }

    @Stable
    private fun onSpace(connection: InputConnection) {
        val tBCA = connection.getTextBeforeCursor(1, GET_TEXT_WITH_STYLES)
        val tBCB = connection.getTextBeforeCursor(2, GET_TEXT_WITH_STYLES)

        if (tBCA.toString() == " ") handleDotShortcut(connection)
        else if (tBCB.toString() == " i") capitalizeI(connection)
        else {
            //Handle Space and Spelling Checker
            connection.commitText(" ", " ".length).let {
                if (it) checkSpelling(connection)
            }
        }
    }

    @Stable
    private fun onText(connection: InputConnection, key: Key) {
        connection.commitText(
            (if (isAllCaps.value as Boolean) {
                key.value.uppercase(Locale.getDefault())
            } else key.value.lowercase(Locale.getDefault())), key.value.length
        )
        getNextSuggestions(connection)
        handleCapsLock()
    }

    @Stable
    private fun onAction(connection: InputConnection, ctx: Context, action: String) {
        if (action == "return") {
            connection.commitText("\n", "\n".length)
        } else (ctx as IMEService).sendDefaultEditorAction(false)
    }

    @Stable
    private fun onErase(context: Context) {
        val connection = (context as IMEService).currentInputConnection
        val selectedText = connection.getSelectedText(GET_TEXT_WITH_STYLES)
        val text = connection.getTextBeforeCursor(16, 0)?.split(" ")?.last()

        if (selectedText != null) {
            connection.commitText("", "".length)
        } else if (text?.codePoints() != null && text.codePoints().toArray().isNotEmpty()) {
            connection.deleteSurroundingTextInCodePoints(1, 0)
        } else connection.deleteSurroundingText(1, 0)

        getNextSuggestions(connection)
        handleCapsLock()
    }

    @Stable
    private fun onShift() {
        if (isCapsLock.value == true) {
            isCapsLock.value = false
            isAllCaps.value = false
        } else if (isAllCaps.value!! && !(isCapsLock.value!!)) {
            onCapsLock()
        } else isAllCaps.value = !(isAllCaps.value!!)
    }

    @Stable
    private fun onCapsLock() {
        viewModelScope.launch {
            val value = preferences.getPreference(pC.ENABLE_CAPS_LOCK_KEY, true)
            if (value) isCapsLock.value = true //isAllCaps is already true.
            else isAllCaps.value = false
        }
    }

    @Stable
    private fun on123Tap() {
        keyboardType.value = KeyboardType.Symbol
    }

    @Stable
    private fun onNumSymbol() {
        isNumberSymbol.value = !(isNumberSymbol.value!!)
    }

    @Stable
    private fun onEmoji() {
        keyboardType.value = KeyboardType.Emoji
    }
}
