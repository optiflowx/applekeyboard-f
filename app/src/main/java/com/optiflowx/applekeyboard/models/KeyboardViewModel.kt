package com.optiflowx.applekeyboard.models

import android.app.LocaleManager
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.SoundPool
import android.os.Build
import android.os.LocaleList
import android.os.VibrationEffect
import android.util.Log
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnection.GET_TEXT_WITH_STYLES
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
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
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.utils.KeyboardType
import kotlinx.coroutines.launch
import splitties.systemservices.vibrator
import java.util.Locale

@Stable
class KeyboardViewModel(screenWidth: Int, colorScheme: ColorScheme, ctx: Context) : ViewModel() {

    //UI
    val isAllCaps = MutableLiveData(false)
    val keyboardType = MutableLiveData(KeyboardType.Normal)
    val isNumberSymbol = MutableLiveData(false)
    val actionButtonText = MutableLiveData("return")
    val actionButtonColor = MutableLiveData(colorScheme.background)
    val actionTextColor = MutableLiveData(colorScheme.inversePrimary)
    val isCapsLock = MutableLiveData(false)
    val keyboardSize = MutableLiveData(IntOffset(screenWidth, 275 + 50))
    val showPopup = MutableLiveData(false)

    val isPhoneSymbol = MutableLiveData(false)
    val currentLanguage = MutableLiveData("en")
    val isEmojiSearch = MutableLiveData(false)
    var soundPool: SoundPool? = null

    //Actions
    val actionDone = MutableLiveData("done")
    val actionSearch = MutableLiveData("search")
    val actionGo = MutableLiveData("go")
    val actionNext = MutableLiveData("next")
    val actionSend = MutableLiveData("send")
    val actionDefault = MutableLiveData("return")

    //DAO
    var frequentlyUsedEmojiDao: FrequentlyUsedEmojiDatabaseDAO? = null

    //Dictionaries
    val wordsDictionary = MutableLiveData(listOf<String>())

    //Private Variables
    private var frequentlyUsedDatabase: FrequentlyUsedDatabase? = null
    private val englishWords = (enListA + enListB + enListC + enListD).toSet()
    private val spanishWords = (spListA + spListB + spListC + spListD).toSet()
    private val ptWords = (ptListA + ptListB + ptListC + ptListD).toSet()
    private val frenchWords = (frListA + frListB + frListC + frListD).toSet()

    init {
        if (soundPool == null) {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .build()
            soundPool = SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(64)
                .build()
        }

        if (frequentlyUsedDatabase == null) {
            frequentlyUsedDatabase =
                Room.inMemoryDatabaseBuilder(ctx, FrequentlyUsedDatabase::class.java)
                    .allowMainThreadQueries()
                    .build()

            frequentlyUsedEmojiDao = frequentlyUsedDatabase?.fUsedEmojiDatabaseDAO()
        }
    }

    override fun onCleared() {
        super.onCleared()
        //Close Sound Pool
        soundPool?.release()
        soundPool = null

        //Close DB
        frequentlyUsedDatabase?.close()
        frequentlyUsedDatabase = null
        frequentlyUsedEmojiDao = null
    }

    private fun playSound(soundID: Int?) {
        if (soundID != null) {
            soundPool?.play(soundID, 1f, 1f, 1, 0, 1.5f)
        }
    }

    private fun handleCapsLock() {
        if (isAllCaps.value == true && isCapsLock.value == false) {
            isAllCaps.value = false
        }
    }

    private fun getNextSuggestions(connection: InputConnection) {
        val charSequence = connection.getTextBeforeCursor(24, GET_TEXT_WITH_STYLES)
        if (charSequence != null) {
            val filterText = charSequence.split(" ").last()
            wordsDictionary.value = when (currentLanguage.value) {
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

    private fun vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, -1))
        }
    }

    private fun checkSpelling(context: Context) {
        val connection = (context as IMEService).currentInputConnection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            connection.performSpellCheck()
        }
    }

    fun changeLocale(locale: String) {
        viewModelScope.launch {
            currentLanguage.value = locale
            showPopup.value = false
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
        viewModelScope.launch {
            val connection = (context as IMEService).currentInputConnection
            connection.commitText(emoji, emoji.length)

            if (!title.lowercase().contains("frequently")) {
                val id = emoji.codePointAt(0)
                val data = frequentlyUsedEmojiDao?.getEmojisById(id)
                val all = frequentlyUsedEmojiDao?.getAllEmojis()?.value

                if (data != null && data.emoji == emoji) {
                    frequentlyUsedEmojiDao?.delete(data).let {
                        frequentlyUsedEmojiDao?.insert(data)
                    }
                } else if (data != null && all != null && all.size == 18) {
                    val last: FrequentlyUsedEmoji = all.last()
                    frequentlyUsedEmojiDao?.delete(last).let {
                        frequentlyUsedEmojiDao?.insert(FrequentlyUsedEmoji(id = id, emoji = emoji))
                    }
                } else frequentlyUsedEmojiDao?.insert(FrequentlyUsedEmoji(id = id, emoji = emoji))
            }
        }
    }

    fun onTKeyClick(key: Key, ctx: Context, soundID: Int?) {
        viewModelScope.launch {
            val connection = (ctx as IMEService).currentInputConnection

            when (key.id) {
                "ABC" -> {
                    keyboardType.value = KeyboardType.Normal
                    keyboardSize.value = keyboardSize.value!!.copy(
                        y = keyboardSize.value!!.y + 48
                    )
                    playSound(soundID)
                }

                "123" -> {
                    keyboardType.value = KeyboardType.Symbol
                    keyboardSize.value = keyboardSize.value!!.copy(
                        x = keyboardSize.value!!.x,
                        y = keyboardSize.value!!.y - 48
                    )
                    playSound(soundID)
                }

                "action" -> {
                    ctx.sendDefaultEditorAction(false)
                    playSound(soundID)
                }

                "space" -> {
                    val textBeforeCursor = connection.getTextBeforeCursor(1, GET_TEXT_WITH_STYLES)
                    if (textBeforeCursor.toString() == " ") {
                        connection.deleteSurroundingText(1, 0).let {
                            connection.commitText(". ", ". ".length)
                        }
                    } else if (textBeforeCursor.toString() == "i") {
                        connection.deleteSurroundingText(1, 0).let {
                            if (it) connection.commitText("I ", "I ".length)
                        }
                    } else connection.commitText(" ", " ".length)
                    playSound(soundID)
                }

                else -> {
                    connection.commitText(
                        (if (isAllCaps.value as Boolean) {
                            key.value.uppercase(Locale.getDefault())
                        } else key.value.lowercase(Locale.getDefault())), key.value.length
                    )
                    getNextSuggestions(connection)
                    playSound(soundID)
                    checkSpelling(ctx)
                }
            }.let {
                handleCapsLock()
                vibrate()
            }
        }
    }

    fun onNumKeyClick(key: Key, ctx: Context, soundID: Int?) {
        viewModelScope.launch {
            val connection = (ctx as IMEService).currentInputConnection

            when (key.value) {
                "*" -> connection.commitText(key.value, key.value.length)
                "#" -> connection.commitText(key.value, key.value.length)
                "+" -> connection.commitText(key.value, key.value.length)
                "wait" -> connection.commitText(";", ";".length)
                "pause" -> connection.commitText(".", ".".length)
                else -> connection.commitText(key.id, key.id.length)
            }

            playSound(soundID)
        }
        vibrate()
    }

    fun onIKeyClick(key: Key, ctx: Context, soundID: Int?) {
        viewModelScope.launch {
            val connection = (ctx as IMEService).currentInputConnection
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
                    checkSpelling(ctx)
                    playSound(soundID)
                }

                "shift" -> {
                    if (isCapsLock.value == true) {
                        isCapsLock.value = false
                        isAllCaps.value = false
                    } else if (isAllCaps.value!! && !(isCapsLock.value!!)) {
                        isCapsLock.value = true //isAllCaps is already true.
                    } else isAllCaps.value = !(isAllCaps.value!!)
                    playSound(soundID)
                }

                "symbol" -> {
                    isNumberSymbol.value = !(isNumberSymbol.value!!)
                    playSound(soundID)
                }
            }
        }
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
