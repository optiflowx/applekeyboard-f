package com.optiflowx.applekeyboard.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnection.GET_TEXT_WITH_STYLES
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
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
import com.optiflowx.applekeyboard.utils.KeyboardType
import com.optiflowx.applekeyboard.utils.booleanLiveData
import com.optiflowx.applekeyboard.utils.intLiveData
import com.optiflowx.applekeyboard.utils.stringLiveData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import splitties.systemservices.vibrator
import java.util.Locale

@Stable
class KeyboardViewModel(screenWidth: Int, colorScheme: ColorScheme, context: Context) :
    ViewModel() {
    //AppViewModel
    private val prefsFileName = "com.optiflowx.applekeyboard"
    private val prefsMode = Context.MODE_PRIVATE
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFileName, prefsMode)

    //UI
    val isAllCaps = MutableLiveData(false)
    val keyboardType = MutableLiveData(KeyboardType.Normal)
    val isNumberSymbol = MutableLiveData(false)
    val actionButtonText = MutableLiveData("return")
    val actionButtonColor = MutableLiveData(colorScheme.background)
    val actionTextColor = MutableLiveData(colorScheme.inversePrimary)
    val isCapsLock = MutableLiveData(false)
    val keyboardSize = MutableLiveData(IntOffset(screenWidth, 275 + 50))

    val isPhoneSymbol = MutableLiveData(false)
    val isEmojiSearch = MutableLiveData(false)
    var soundPool: SoundPool? = null

    //Actions
    val actionDone = MutableLiveData("done")
    val actionSearch = MutableLiveData("search")
    val actionGo = MutableLiveData("go")
    val actionNext = MutableLiveData("next")
    val actionSend = MutableLiveData("send")
    val actionDefault = MutableLiveData("return")

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

    //App Data
    val locale = prefs.stringLiveData("locale", "en").asFlow()
    val fontType = prefs.stringLiveData("fontType", "regular").asFlow()
    val fontSize = prefs.intLiveData("fontSize", 18).asFlow()
    private val vibrateOnKeyPress = prefs.booleanLiveData("vibrateOnKeyPress", true).asFlow()
    private val soundOnKeyPress = prefs.booleanLiveData("soundOnKeyPress", true).asFlow()
    private val autoCapitalize = prefs.booleanLiveData("autoCapitalize", true).asFlow()
    private val autoCorrect = prefs.booleanLiveData("autoCorrect", false).asFlow()
    private val doubleSpacePeriod = prefs.booleanLiveData("doubleSpacePeriod", true).asFlow()
    private val autoCapitalizeFirstWord =
        prefs.booleanLiveData("autoCapitalizeFirstWord", false).asFlow()
    private val autoCapitalizeI = prefs.booleanLiveData("autoCapitalizeI", true).asFlow()
    private val showSuggestions = prefs.booleanLiveData("showSuggestions", true).asFlow()
    private val showEmojiSearchBar = prefs.booleanLiveData("showEmojiSearchBar", true).asFlow()
    private val autoCheckSpelling = prefs.booleanLiveData("autoCheckSpelling", false).asFlow()

    init {
        //Init Sound Pool
        if (soundPool == null) {
            val audioAttributes =
                AudioAttributes.Builder().setContentType(CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).build()
            soundPool =
                SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(128).build()
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

    private fun playSound(soundID: Int?) {
        viewModelScope.launch {
            soundOnKeyPress.collectLatest {
                if (it) soundPool?.play(soundID!!, 1f, 1f, 1, 0, 1.5f)
            }
        }

    }

    @Suppress("DEPRECATION")
    private fun vibrate() {
        viewModelScope.launch {
            vibrateOnKeyPress.collectLatest {
                if (it) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(50, -1))
                    } else vibrator.vibrate(50)
                }
            }
        }

    }

    private fun checkSpelling(context: Context) {
        viewModelScope.launch {
            autoCheckSpelling.collectLatest {
                if (it) {
                    val connection = (context as IMEService).currentInputConnection
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        connection.performSpellCheck()
                    }
                }
            }
        }

    }

    private fun handleCapsLock() {
        viewModelScope.launch {
            autoCapitalize.collectLatest {
                if (it) {
                    if (isAllCaps.value == true && isCapsLock.value == false) {
                        isAllCaps.value = false
                    }
                }
            }
        }
    }

    private fun updateCapsLock() {
        viewModelScope.launch {
            autoCapitalize.collectLatest {
                if (it) {
                    isCapsLock.value = false
                    isAllCaps.value = true
                }
            }
        }
    }

    private fun handleAutoCorrect() {
        viewModelScope.launch {
            autoCorrect.collectLatest {
//                if (it) { }
            }
        }
    }

    private fun handleAutoCapitalizeFirstWord(connection: InputConnection) {
        viewModelScope.launch {
            autoCapitalizeFirstWord.collectLatest {
                if (it) {
                    val text = connection.getTextBeforeCursor(2, GET_TEXT_WITH_STYLES)
                    if(text.isNullOrEmpty()) updateCapsLock()
                }
            }
        }
    }

    private fun handleDoubleSpacePeriod(connection: InputConnection) {
        viewModelScope.launch {
            doubleSpacePeriod.collectLatest {
                if (it) {
                    connection.deleteSurroundingText(1, 0).let {
                        connection.commitText(". ", ". ".length)
                    }
                    updateCapsLock()
                }
            }
        }
    }

    private fun handleAutoCapitalizeI(connection: InputConnection) {
        viewModelScope.launch {
            autoCapitalizeI.collectLatest {
                if (it) {
                    connection.deleteSurroundingText(1, 0).let {
                        connection.commitText(" I ", " I ".length)
                    }
                }
            }
        }
    }

    private fun getNextSuggestions(connection: InputConnection) {
        val charSequence = connection.getTextBeforeCursor(24, GET_TEXT_WITH_STYLES)
        if (charSequence != null) {
            val filterText = charSequence.split(" ").last()
            viewModelScope.launch {
                locale.collectLatest { language ->
                    wordsDictionary.value = when (language) {
                        "pt" -> ptWords.filter {
                            it.startsWith(filterText.lowercase())
                        }

                        "es" -> spanishWords.filter {
                            it.startsWith(filterText.lowercase())
                        }

                        "fr" -> frenchWords.filter {
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

    private fun handleShowSuggestions(connection: InputConnection) {
        viewModelScope.launch {
            showSuggestions.collectLatest {
                if (it) getNextSuggestions(connection)
            }
        }
    }

    fun onSuggestionClick(suggestion: String, context: Context) {
        viewModelScope.launch {
            val connection = (context as IMEService).currentInputConnection
            val charSequence = connection.getTextBeforeCursor(24, GET_TEXT_WITH_STYLES)

            if (charSequence != null) {
                val textToReplace = charSequence.split(" ").last()
                connection.deleteSurroundingText(textToReplace.length, 0).let {
                    if (it) connection.commitText("$suggestion ", suggestion.length + 1)
                }
            }
        }
    }

    fun onEmojiClick(context: Context, emoji: String, title: String) {
        viewModelScope.launch {
            val connection = (context as IMEService).currentInputConnection
            connection.commitText(emoji, emoji.length)

            if (!title.lowercase().contains("frequently")) {
                val id = emoji.codePointAt(0)
                val data = fuEmojiDbDAO.getEmojisById(id)
                val all = fuEmojiDbDAO.getAllEmojis().value

                if (data != null && data.emoji != emoji) {
//                    fuEmojiDbDAO.delete(data).let { fuEmojiDbDAO.insert(data) }
                    fuEmojiDbDAO.insert(FrequentlyUsedEmoji(id = id, emoji = emoji))
                } else if (data != null && all != null && all.size == 18) {
                    val last: FrequentlyUsedEmoji = all.last()
                    fuEmojiDbDAO.delete(last).let {
                        fuEmojiDbDAO.insert(FrequentlyUsedEmoji(id = id, emoji = emoji))
                    }
                }
            }
        }
    }

    fun onTKeyClick(key: Key, context: Context, soundID: Int?) {
        viewModelScope.launch {
            val connection = (context as IMEService).currentInputConnection
            when (key.id) {
                "ABC" -> {
                    keyboardType.value = KeyboardType.Normal
                    keyboardSize.value = keyboardSize.value!!.copy(
                        y = keyboardSize.value!!.y + 48
                    )
                }

                "123" -> {
                    keyboardType.value = KeyboardType.Symbol
                    keyboardSize.value = keyboardSize.value!!.copy(
                        x = keyboardSize.value!!.x, y = keyboardSize.value!!.y - 48
                    )
                }

                "action" -> context.sendDefaultEditorAction(false)

                "space" -> {
                    val textBeforeCursor = connection.getTextBeforeCursor(1, GET_TEXT_WITH_STYLES)
                    if (textBeforeCursor.toString() == " ") {
                        handleDoubleSpacePeriod(connection)
                    } else if (textBeforeCursor.toString() == " i") {
                        handleAutoCapitalizeI(connection)
                    } else connection.commitText(
                        " ",
                        " ".length
                    )
                }

                else -> {
                    connection.commitText(
                        (if (isAllCaps.value as Boolean) {
                            key.value.uppercase(Locale.getDefault())
                        } else key.value.lowercase(Locale.getDefault())), key.value.length
                    )

                    handleShowSuggestions(connection)
                    checkSpelling(context)
                    handleCapsLock()
                }
            }
        }
        playSound(soundID)
        vibrate()
    }

    fun onNumKeyClick(key: Key, context: Context, soundID: Int?) {
        viewModelScope.launch {
            val connection = (context as IMEService).currentInputConnection
            when (key.value) {
                "*" -> connection.commitText(key.value, key.value.length)
                "#" -> connection.commitText(key.value, key.value.length)
                "+" -> connection.commitText(key.value, key.value.length)
                "wait" -> connection.commitText(";", ";".length)
                "pause" -> connection.commitText(".", ".".length)
                else -> connection.commitText(key.id, key.id.length)
            }
        }
        playSound(soundID)
        vibrate()
    }

    fun onIKeyClick(key: Key, context: Context, soundID: Int?) {
        viewModelScope.launch {
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
        playSound(soundID)
        vibrate()
    }

    fun onABCTap() {
        viewModelScope.launch {
            keyboardSize.value = keyboardSize.value!!.copy(
                y = keyboardSize.value?.y!! - 30
            )
            keyboardType.value = KeyboardType.Normal
        }
    }

    fun onEmojiTap() {
        viewModelScope.launch {
            if (keyboardType.value == KeyboardType.Symbol) {
                val oldValue = 48
                keyboardSize.value = keyboardSize.value!!.copy(
                    y = keyboardSize.value?.y!! + oldValue + 30
                )
            } else keyboardSize.value = keyboardSize.value!!.copy(
                y = keyboardSize.value?.y!! + 30
            )
            keyboardType.value = KeyboardType.Emoji
        }
    }

    fun onPhoneSymbol() {
        viewModelScope.launch {
            isPhoneSymbol.value = !(isPhoneSymbol.value!!)
        }
    }
}
