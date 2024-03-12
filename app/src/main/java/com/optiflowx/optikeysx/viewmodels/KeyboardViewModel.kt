package com.optiflowx.optikeysx.viewmodels

import android.content.ClipboardManager
import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.algorithm.DictationProcessor
import com.optiflowx.optikeysx.core.data.KeyboardData
import com.optiflowx.optikeysx.core.database.dao.ClipboardDatabaseDAO
import com.optiflowx.optikeysx.core.database.dao.FrequentlyUsedEmojiDatabaseDAO
import com.optiflowx.optikeysx.core.database.dbs.ClipboardDatabase
import com.optiflowx.optikeysx.core.database.dbs.FrequentlyUsedDatabase
import com.optiflowx.optikeysx.core.database.entities.ClipData
import com.optiflowx.optikeysx.core.database.entities.FrequentlyUsedEmoji
import com.optiflowx.optikeysx.core.enums.KeyboardType
import com.optiflowx.optikeysx.core.model.Key
import com.optiflowx.optikeysx.core.utils.KeyboardLocale
import com.optiflowx.optikeysx.ime.IMEService
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
import com.optiflowx.optikeysx.optikeysxPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import splitties.systemservices.vibrator
import java.util.Locale

@Stable
class KeyboardViewModel(context: Context) : ViewModel() {
    private val _keyboardType = MutableStateFlow(KeyboardType.Normal)
    val keyboardType = _keyboardType.asStateFlow()

    private val _isNumberSymbol = MutableStateFlow(false)
    val isNumberSymbol = _isNumberSymbol.asStateFlow()

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

    private val _keyboardData = MutableStateFlow(KeyboardData())
    val keyboardData = _keyboardData.asStateFlow()

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
    private val vol = 0.44f
    private val rate = 1.1f

    //DAO
    private lateinit var _fuEmojiDbDAO: FrequentlyUsedEmojiDatabaseDAO
    private lateinit var _clipDataDbDAO: ClipboardDatabaseDAO

    //Databases
    private lateinit var _fuEmojiDB: FrequentlyUsedDatabase
    private lateinit var _clipboardDB: ClipboardDatabase

    //Database com.optiflowx.optikeysx.core.Utils
    lateinit var frequentlyUsedEmojis: LiveData<List<FrequentlyUsedEmoji>>
    lateinit var clipData: LiveData<List<ClipData>>

    //DictationProcessor
    private lateinit var _dictationProcessor: DictationProcessor
    private lateinit var _dictionary: Set<String>

    private val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    //UI Locale
    val keyboardLocale = KeyboardLocale(keyboardData.value.locale)

    val prefs by optikeysxPreferences()

    init {
        initSoundPool()

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

        viewModelScope.launch(Dispatchers.IO) {
            keyboardData.collectLatest { data ->
                _dictionary = when (data.locale) {
                    "fr-FR" -> frenchWords
                    "es" -> spanishWords
                    "pt-BR" -> ptWords
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

    @Stable
    fun initKeyboardData(data: KeyboardData) {
        viewModelScope.launch(Dispatchers.IO) {
            _keyboardData.value = data
        }
    }

    @Stable
    fun updateIsEmojiSearch(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _isEmojiSearch.value = value
        }
    }

    @Stable
    fun updateIsShowOptions(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _isShowOptions.value = value
        }
    }

    @Stable
    fun updateIMEActions(colorA: Color, colorB: Color, text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _keyActionButtonColor.value = colorA
            _keyActionTextColor.value = colorB
            _keyActionText.value = text
        }
    }

    @Stable
    private fun initSoundPool() = viewModelScope.launch {
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
        viewModelScope.launch {
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

    @Stable
    fun playSound(key: Key) = viewModelScope.launch {
        if (prefs.isSoundOnKeypress.get() && _isPoolLoaded.value) {
            when (key.id) {
                "delete" -> _soundPool.play(_soundIDD.value, vol, vol, 1, 0, rate)
                "return" -> _soundPool.play(_soundIDR.value, vol, vol, 1, 0, rate)
                "space" -> _soundPool.play(_soundIDS.value, vol, vol, 2, 0, rate)
                else -> _soundPool.play(_soundIDT.value, vol, vol, 3, 0, rate)
            }
        }
    }

    @Stable
    fun vibrate() {
        if (prefs.isVibrateOnKeypress.get()) {
            @Suppress("DEPRECATION")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(35, -1))
            } else vibrator.vibrate(35)
        }
    }

    @Stable
    fun pasteTextFromClipboard(text: String, context: Context) {
        val ic = (context as IMEService).currentInputConnection
        ic.commitText(text, text.length)
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
    private fun checkSpelling(ic: InputConnection) {
        viewModelScope.launch(Dispatchers.IO) {
            val checkSpelling = prefs.isCheckSpelling.get()
            val autoCorrect = prefs.isAutoCorrect.get()
            //Remove the space first
            if (checkSpelling && autoCorrect) {
                this.launch(Dispatchers.IO) {
                    ic.deleteSurroundingText(1, 0).let {
                        val tBC = ic.getTextBeforeCursor(12, 0)
                        if (tBC != null) {
                            val lastWord = tBC.split(" ").last()

                            this.launch(Dispatchers.IO) {
                                val correction = _dictationProcessor.correct(lastWord)
                                ic.deleteSurroundingText(lastWord.length, 0).let { value ->
                                    if (value) ic.commitText("$correction ", 0)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Stable
    private fun handleDotShortcut(ic: InputConnection) {
        viewModelScope.launch {
            if (prefs.isDotShortcut.get()) {
                ic.deleteSurroundingText(1, 0).let {
                    ic.commitText(". ", ". ".length)
                }
                updateCapsLock()
            }
        }
    }

    @Stable
    private fun capitalizeI(ic: InputConnection) {
        viewModelScope.launch {
            if (prefs.isAutoCapitalisation.get()) {
                ic.deleteSurroundingText(2, 0).let {
                    ic.commitText(" I ", " I ".length)
                }
            }
        }
    }

    @Stable
    fun updateSuggestionsState() {
        viewModelScope.launch {
            _wordsDictionary.value = wordsDictionary.value.map { value ->
                if (prefs.isAllCaps.get() && !prefs.isCapsLock.get()) {
                    value.replaceFirstChar { it.titlecase(Locale.getDefault()) }
                } else if (prefs.isAllCaps.get() && prefs.isCapsLock.get()) {
                    value.uppercase(Locale.getDefault())
                } else value.lowercase(Locale.getDefault())
            }
        }
    }

    @Stable
    private fun getNextSuggestions(ic: InputConnection) {
        if (prefs.isPredictive.get()) {
            val charSequence = ic.getTextBeforeCursor(24, 0)
            if (charSequence != null) {
                val filterText = charSequence.split(" ").last()

                viewModelScope.launch {
                    _wordsDictionary.value = _dictionary.filter {
                        it.startsWith(filterText.lowercase())
                    }.map { value ->
                        if (prefs.isAllCaps.get() && !prefs.isCapsLock.get()) {
                            value.replaceFirstChar { it.titlecase(Locale.getDefault()) }
                        } else if (prefs.isAllCaps.get() && prefs.isCapsLock.get()) {
                            value.uppercase(Locale.getDefault())
                        } else value.lowercase(Locale.getDefault())
                    }
                }
            }
        }
    }

    @Stable
    fun onSuggestionClick(suggestion: String, context: Context) {
        if (suggestion.isNotEmpty()) {
            val ic = (context as IMEService).currentInputConnection
            val charSequence = ic.getTextBeforeCursor(24, 0)

            if (charSequence != null) {
                val textToReplace = charSequence.split(" ").last()
                ic.deleteSurroundingText(textToReplace.length, 0).let {
                    if (it) ic.commitText("$suggestion ",  suggestion.chars().count().toInt())
                }
            }
        }
    }

    @Stable
    fun onEmojiClick(context: Context, emoji: String, title: String) {
        val ic = (context as IMEService).currentInputConnection
        ic.commitText(emoji, emoji.length)

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
    fun onTKeyClick(key: Key, context: Context) {
        val ic = (context as IMEService).currentInputConnection

        when (key.id) {
            "ABC" -> onUpdateKeyboardType(KeyboardType.Normal)

            "123" -> onUpdateKeyboardType(KeyboardType.Symbol)

            "action" -> onAction(ic)

            "space" -> onSpace(ic)

            else -> onText(ic, key)
        }
    }

    @Stable
    fun onNumKeyClick(key: Key, context: Context) {
        val ic = (context as IMEService).currentInputConnection
        when (key.value) {
            "*" -> ic.commitText(key.value, key.value.length)
            "#" -> ic.commitText(key.value, key.value.length)
            "+" -> ic.commitText(key.value, key.value.length)
            keyboardLocale.wait() -> ic.commitText(";", ";".length)
            keyboardLocale.pause() -> ic.commitText(".", ".".length)
            else -> {
                if (key.id == "period") ic.commitText(".", ".".length)
                else ic.commitText(key.id, key.id.length)
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
        if (newType == KeyboardType.Normal) {
            _wordsDictionary.value = listOf()
        }

        _keyboardType.value = newType
    }

    @Stable
    fun onPhoneSymbol() {
        viewModelScope.launch {
            _isPhoneSymbol.value = !(_isPhoneSymbol.value)
        }
    }

    @Stable
    private fun onSpace(ic: InputConnection) {
        val tBCA = ic.getTextBeforeCursor(1, 0)
        val tBCB = ic.getTextBeforeCursor(2, 0)

        if (tBCA.toString() == " ") handleDotShortcut(ic)
        else if (tBCB.toString() == " i") capitalizeI(ic)
        else {
            //Handle Space and Spelling Checker
            ic.commitText(" ", " ".length).let {
                if (it) checkSpelling(ic)
            }
        }
    }

    @Stable
    private fun onText(ic: InputConnection, key: Key) {
        ic.commitText(
            (if (prefs.isAllCaps.get()) {
                key.value.uppercase()
            } else key.value.lowercase()), key.value.length
        )
        getNextSuggestions(ic)
        handleCapsLock()
    }

    @Stable
    private fun onAction(ic: InputConnection) {
        val EA: Int = keyboardData.value.enterAction
        if ((EA == EditorInfo.IME_ACTION_UNSPECIFIED || EA == EditorInfo.IME_ACTION_NONE)) {
            ic.commitText("\n", "\n".length)
        } else {
            ic.performEditorAction(EA)
        }
    }

    @Stable
    private fun onErase(context: Context) {
        val ic = (context as IMEService).currentInputConnection
        val text = ic.getTextBeforeCursor(12, 0)?.split(" ")?.last()
        val selectedText = ic.getSelectedText(0)
        val data = ic.getTextBeforeCursor(2, 0)

        if ((data.isNullOrEmpty() || data == "") && TextUtils.isEmpty(selectedText)) {
            updateCapsLock()
        } else {
            if (selectedText != null && !TextUtils.isEmpty(selectedText)) {
                //Clear all the selected text
                ic.commitText("", 1)
            } else {
                if (text != null && text.codePoints().toArray().isNotEmpty()) {
                    //Delete Emojis or Any Special text with codepoints
                    ic.deleteSurroundingTextInCodePoints(1, 0)
                } else {
                    //Delete Characters
                    ic.deleteSurroundingText(1, 0)
                }
            }

            getNextSuggestions(ic)
            handleCapsLock()
        }
    }

    @Stable
    fun handleCapsLock(text: String): String {
        return if (prefs.isAllCaps.get() && prefs.isCapsLock.get()) {
            text.uppercase(Locale.getDefault())
        } else text.lowercase(Locale.getDefault())
    }

    @Stable
    fun handleAllCaps(text: String): String {
        return if (prefs.isAllCaps.get()) {
            text.replaceFirstChar {
                it.titlecase(Locale.getDefault())
            }
        } else text.lowercase(Locale.getDefault())
    }

    @Stable
    private fun handleCapsLock() {
        viewModelScope.launch(Dispatchers.IO) {
            if (prefs.isAllCaps.get() && !prefs.isCapsLock.get()) {
                prefs.isAllCaps.set(false)
            }
        }
    }

    @Stable
    private fun updateCapsLock() {
        viewModelScope.launch(Dispatchers.IO) {
            if (prefs.isAutoCapitalisation.get()) {
                prefs.isAllCaps.set(true)
                prefs.isCapsLock.set(false)
            }
        }
    }

    @Stable
    private fun onShift() {
        viewModelScope.launch(Dispatchers.IO) {
            if (prefs.isCapsLock.get()) {
                prefs.isAllCaps.set(false)
                prefs.isCapsLock.set(false)
            } else if (prefs.isAllCaps.get() && !prefs.isCapsLock.get()) {
                onCapsLock()
            } else prefs.isAllCaps.set(!prefs.isAllCaps.get())
        }
    }

    @Stable
    private fun onCapsLock() {
        viewModelScope.launch(Dispatchers.IO) {
            val value = prefs.isEnableCapsLock.get()
            if (value) prefs.isCapsLock.set(true)//isAllCaps is already true.
            else prefs.isAllCaps.set(false)
        }
    }

    @Stable
    private fun onNumSymbol() {
        viewModelScope.launch {
            _isNumberSymbol.value = !(_isNumberSymbol.value)
        }
    }
}
