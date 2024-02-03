package com.optiflowx.optikeysx.viewmodels

import android.content.ClipboardManager
import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnection.GET_TEXT_WITH_STYLES
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.algorithm.DictationProcessor
import com.optiflowx.optikeysx.core.data.Key
import com.optiflowx.optikeysx.core.database.dao.ClipboardDatabaseDAO
import com.optiflowx.optikeysx.core.database.dao.FrequentlyUsedEmojiDatabaseDAO
import com.optiflowx.optikeysx.core.database.dbs.ClipboardDatabase
import com.optiflowx.optikeysx.core.database.dbs.FrequentlyUsedDatabase
import com.optiflowx.optikeysx.core.database.entities.ClipData
import com.optiflowx.optikeysx.core.database.entities.FrequentlyUsedEmoji
import com.optiflowx.optikeysx.core.enums.KeyboardLanguage
import com.optiflowx.optikeysx.core.enums.KeyboardType
import com.optiflowx.optikeysx.core.preferences.PreferencesHelper
import com.optiflowx.optikeysx.core.preferences.PrefsConstants
import com.optiflowx.optikeysx.core.services.IMEService
import com.optiflowx.optikeysx.core.utils.KeyboardLocale
import com.optiflowx.optikeysx.languages.english.enListA
import com.optiflowx.optikeysx.languages.english.enListB
import com.optiflowx.optikeysx.languages.english.enListC
import com.optiflowx.optikeysx.languages.english.enListD
import com.optiflowx.optikeysx.languages.french.frListA
import com.optiflowx.optikeysx.languages.french.frListB
import com.optiflowx.optikeysx.languages.french.frListC
import com.optiflowx.optikeysx.languages.french.frListD
import com.optiflowx.optikeysx.languages.portuguese.ptListA
import com.optiflowx.optikeysx.languages.portuguese.ptListB
import com.optiflowx.optikeysx.languages.portuguese.ptListC
import com.optiflowx.optikeysx.languages.portuguese.ptListD
import com.optiflowx.optikeysx.languages.spanish.spListA
import com.optiflowx.optikeysx.languages.spanish.spListB
import com.optiflowx.optikeysx.languages.spanish.spListC
import com.optiflowx.optikeysx.languages.spanish.spListD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import splitties.systemservices.vibrator

@Stable
class KeyboardViewModel(context: Context) : ViewModel() {

    //UI FLows
    private val _isAllCaps = MutableStateFlow(false)
    val isAllCaps = _isAllCaps.asStateFlow()

    private val _keyboardType = MutableStateFlow(KeyboardType.Normal)
    val keyboardType = _keyboardType.asStateFlow()

    private val _isNumberSymbol = MutableStateFlow(false)
    val isNumberSymbol = _isNumberSymbol.asStateFlow()

    private val _isCapsLock = MutableStateFlow(false)
    val isCapsLock = _isCapsLock.asStateFlow()

    private val _isPhoneSymbol = MutableStateFlow(false)
    val isPhoneSymbol = _isPhoneSymbol.asStateFlow()

    private val _isEmojiSearch = MutableStateFlow(false)
    val isEmojiSearch = _isEmojiSearch.asStateFlow()

    private val _isShowOptions = MutableStateFlow(false)
    val isShowOptions = _isShowOptions.asStateFlow()

    private val _keyActionButtonColor = MutableStateFlow(Color.Transparent)
    val keyActionButtonColor = _keyActionButtonColor.asStateFlow()

    private val _keyActionTextColor = MutableStateFlow(Color.Transparent)
    val keyActionTextColor = _keyActionTextColor.asStateFlow()

    private val _keyActionText = MutableStateFlow("return")
    val keyActionText = _keyActionText.asStateFlow()

    private val _wordsDictionary = MutableStateFlow(listOf<String>())
    val wordsDictionary = _wordsDictionary.asStateFlow()

    //Sound
    private val _isPoolLoaded = MutableStateFlow(false)
    private val _soundIDT = MutableStateFlow(0)
    private val _soundIDD = MutableStateFlow(0)
    private val _soundIDS = MutableStateFlow(0)
    private val _soundIDR = MutableStateFlow(0)

    //Private Variables
    private val englishWords = (enListA + enListB + enListC + enListD).toSet()
    private val spanishWords = (spListA + spListB + spListC + spListD).toSet()
    private val ptWords = (ptListA + ptListB + ptListC + ptListD).toSet()
    private val frenchWords = (frListA + frListB + frListC + frListD).toSet()

    //Sound
    private lateinit var _soundPool: SoundPool

    //DAO
    private lateinit var _fuEmojiDbDAO: FrequentlyUsedEmojiDatabaseDAO
    private lateinit var _clipDataDbDAO: ClipboardDatabaseDAO

    //Databases
    private lateinit var _fuEmojiDB: FrequentlyUsedDatabase
    private lateinit var _clipboardDB: ClipboardDatabase

    //Database Utils
    lateinit var frequentlyUsedEmojis: LiveData<List<FrequentlyUsedEmoji>>
    lateinit var clipData: LiveData<List<ClipData>>

    //DictationProcessor
    private lateinit var _dictationProcessor: DictationProcessor
    private lateinit var _dictionary: Set<String>

    //UI Locale
    private val _keyboardLocale = KeyboardLocale()
    private val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    //DataStore
    private val preferences = PreferencesHelper(context)
    private val pC = PrefsConstants

    init {
        viewModelScope.launch(Dispatchers.IO) {
            initSoundPool()
            //Init Databases
            _fuEmojiDB = FrequentlyUsedDatabase.getInstance(context)
            _clipboardDB = ClipboardDatabase.getInstance(context)

            //Init DAO
            _fuEmojiDbDAO = _fuEmojiDB.fUsedEmojiDatabaseDAO()
            _clipDataDbDAO = _clipboardDB.clipboardDAO()

            //Init LiveData
            frequentlyUsedEmojis = _fuEmojiDbDAO.getAllEmojis()
            clipData = _clipDataDbDAO.getAllClipData()
        }

        clipboardManager.addPrimaryClipChangedListener {
            val clipData = clipboardManager.primaryClip?.getItemAt(0)?.text
            viewModelScope.launch(Dispatchers.IO) {
                if (!clipData.isNullOrEmpty()) {
                    val text = clipData.toString()
                    val id = text.hashCode()

                    _clipDataDbDAO.insert(ClipData(id, text))
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            preferences.getFlowPreference(pC.LOCALE_KEY, "English")
                .collectLatest { locale ->
                    _dictionary = when (locale) {
                        "French" -> frenchWords
                        "Spanish" -> spanishWords
                        "Portuguese" -> ptWords
                        else -> englishWords
                    }

                    _dictationProcessor = DictationProcessor(_dictionary)
                }
        }
    }

    override fun onCleared() {
        super.onCleared()

        _fuEmojiDB.close()
        _clipboardDB.close()
        _soundPool.release()

        clipboardManager.removePrimaryClipChangedListener {}
    }

//    fun updateIsAllCaps(value: Boolean) {
//        viewModelScope.launch {
//            _isAllCaps.value = value
//        }
//    }

    fun updateIsEmojiSearch(value: Boolean) {
        viewModelScope.launch {
            _isEmojiSearch.value = value
        }
    }

    fun updateIsShowOptions(value: Boolean) {
        viewModelScope.launch {
            _isShowOptions.value = value
        }
    }

    @Stable
    fun updateIMEActions(colorA: Color, colorB: Color, text: String) {
        viewModelScope.launch {
            _keyActionButtonColor.value = colorA
            _keyActionTextColor.value = colorB
            _keyActionText.value = text
        }
    }

    private fun initSoundPool() = viewModelScope.launch(Dispatchers.IO) {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        _soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()
    }

    @Stable
    fun initSoundIDs(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _soundIDT.value = _soundPool.load(context, R.raw.standard, 1)
            _soundIDD.value = _soundPool.load(context, R.raw.delete, 1)
            _soundIDS.value = _soundPool.load(context, R.raw.spacebar, 1)
            _soundIDR.value = _soundPool.load(context, R.raw.ret, 1)

            _soundPool.setOnLoadCompleteListener { _, _, status ->
                _isPoolLoaded.value = (status == 0)
            }
        }
    }

    @Stable
    fun onDisposeSoundIDs() {
        viewModelScope.launch {
            viewModelScope.launch {
                _soundPool.unload(_soundIDT.value)
                _soundPool.unload(_soundIDD.value)
                _soundPool.unload(_soundIDS.value)
                _soundPool.unload(_soundIDR.value)
            }.invokeOnCompletion {
                _isPoolLoaded.value = false
                _soundIDT.value = 0
                _soundIDD.value = 0
                _soundIDS.value = 0
                _soundIDR.value = 0
            }
        }
    }


    @Stable
    fun playSound(key: Key) = viewModelScope.launch(Dispatchers.IO) {
        val value: Boolean = preferences.getPreference(pC.SOUND_ON_KEY_PRESS_KEY, true)
        if (value && _isPoolLoaded.value) {
            when (key.id) {
                "delete" -> _soundPool.play(_soundIDD.value, 0.5f, 0.5f, 1, 0, 1.05f)
                "return" -> _soundPool.play(_soundIDR.value, 0.5f, 0.5f, 1, 0, 1.05f)
                "space" -> _soundPool.play(_soundIDS.value, 0.5f, 0.5f, 1, 0, 1.05f)
                else -> _soundPool.play(_soundIDT.value, 0.5f, 0.5f, 1, 0, 1.05f)
            }
        }
    }

    @Stable
    fun vibrate() = viewModelScope.launch(Dispatchers.IO) {
        val value: Boolean = preferences.getPreference(pC.VIBRATE_ON_KEY_PRESS_KEY, true)
        if (value) {
            @Suppress("DEPRECATION")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(45, -1))
            } else vibrator.vibrate(45)
        }
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
                _clipDataDbDAO.clear(data)
            }
        }
    }

    @Stable
    private fun checkSpelling(connection: InputConnection) {
        viewModelScope.launch(Dispatchers.IO) {
            val checkSpelling: Boolean = preferences.getPreference(pC.CHECK_SPELLING_KEY, false)
            val autoCorrect = preferences.getPreference(pC.AUTO_CORRECTION_KEY, false)
            //Remove the space first
            if (checkSpelling && autoCorrect) {
                this.launch(Dispatchers.IO) {
                    connection.deleteSurroundingText(1, 0).let {
                        val tBC = connection.getTextBeforeCursor(12, GET_TEXT_WITH_STYLES)
                        if (tBC != null) {
                            val lastWord = tBC.split(" ").last()

                            this.launch(Dispatchers.IO) {
                                val correction = _dictationProcessor.correct(lastWord)
                                connection.deleteSurroundingText(lastWord.length, 0).let { value ->
                                    if (value) connection.commitText("$correction ", 0)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Stable
    private fun handleCapsLock() {
        if (_isAllCaps.value && !_isCapsLock.value) {
            _isAllCaps.value = false
        }
    }

    @Stable
    private suspend fun updateCapsLock() {
        val value: Boolean = preferences.getPreference(pC.AUTO_CAPITALISATION_KEY, true)
        if (value) {
            _isAllCaps.value = true
            _isCapsLock.value = false
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
            _wordsDictionary.value = _dictionary.filter {
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
                val data = _fuEmojiDbDAO.getEmojisById(id)
                val all = _fuEmojiDbDAO.getAllEmojis().value

                if (data != null && data.emoji == emoji) {
                    _fuEmojiDbDAO.deleteAndInsertEmoji(data, data)
                } else if (all?.isNotEmpty() == true && all.size >= 18) {
                    val oldData = all.last()
                    val newData = FrequentlyUsedEmoji(id, emoji)

                    _fuEmojiDbDAO.deleteAndInsertEmoji(newData, oldData)
                } else _fuEmojiDbDAO.insert(FrequentlyUsedEmoji(id = id, emoji = emoji))
            }
        }
    }

    @Stable
    fun onTKeyClick(key: Key, context: Context, action: String = "return") {
        val connection = (context as IMEService).currentInputConnection

        when (key.id) {
            "ABC" -> onUpdateKeyboardType(KeyboardType.Normal)

            "123" -> onUpdateKeyboardType(KeyboardType.Symbol)

            "action" -> onAction(connection, context, action)

            "space" -> onSpace(connection)

            else -> onText(connection, key)
        }
    }

    @Stable
    fun onNumKeyClick(key: Key, context: Context) {
        val connection = (context as IMEService).currentInputConnection
        viewModelScope.launch(Dispatchers.IO) {
            val it = preferences.getPreference(pC.LOCALE_KEY, KeyboardLanguage.English.name)
            when (key.value) {
                "*" -> connection.commitText(key.value, key.value.length)
                "#" -> connection.commitText(key.value, key.value.length)
                "+" -> connection.commitText(key.value, key.value.length)
                _keyboardLocale.wait(it) -> connection.commitText(";", ";".length)
                _keyboardLocale.pause(it) -> connection.commitText(".", ".".length)
                else -> {
                    if (key.id == "period") connection.commitText(".", ".".length)
                    else connection.commitText(key.id, key.id.length)
                }
            }
        }
    }

    @Stable
    fun onIKeyClick(key: Key, context: Context) {
        when (key.id) {
            "emoji" -> onUpdateKeyboardType(KeyboardType.Emoji)

            "delete" -> onErase(context)

            "shift" -> onShift()

            "symbol" -> onNumSymbol()
        }
    }

    @Stable
    fun onUpdateKeyboardType(newType: KeyboardType) {
        _keyboardType.value = newType
    }

    @Stable
    fun onPhoneSymbol() {
        _isPhoneSymbol.value = !(_isPhoneSymbol.value)
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
            (if (_isAllCaps.value) {
                key.value.uppercase()
            } else key.value.lowercase()), key.value.length
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
        if (_isCapsLock.value) {
            _isCapsLock.value = false
            _isAllCaps.value = false
        } else if (_isAllCaps.value && !(_isCapsLock.value)) {
            onCapsLock()
        } else _isAllCaps.value = !(isAllCaps.value)
    }

    @Stable
    private fun onCapsLock() {
        viewModelScope.launch {
            val value = preferences.getPreference(pC.ENABLE_CAPS_LOCK_KEY, true)
            if (value) _isCapsLock.value = true //isAllCaps is already true.
            else _isAllCaps.value = false
        }
    }

    @Stable
    private fun onNumSymbol() {
        viewModelScope.launch {
            _isNumberSymbol.value = !(_isNumberSymbol.value)
        }
    }
}
