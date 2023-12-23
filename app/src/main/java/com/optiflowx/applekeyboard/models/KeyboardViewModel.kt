package com.optiflowx.applekeyboard.models

import android.content.Context
import android.media.MediaPlayer
import android.view.inputmethod.InputConnection.GET_TEXT_WITH_STYLES
import androidx.compose.material.Colors
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.optiflowx.applekeyboard.adapters.Key
import com.optiflowx.applekeyboard.database.EmojisDatabase
import com.optiflowx.applekeyboard.database.dao.RecentEmojiDatabaseDAO
import com.optiflowx.applekeyboard.database.entities.EmojiData
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.utils.KeyboardType
import com.optiflowx.applekeyboard.utils.first5kWords
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import splitties.experimental.ExperimentalSplittiesApi
import splitties.views.InputType
import java.util.Locale


class KeyboardViewModel(screenWidth: Int, colors: Colors) : ViewModel() {
    var isAllCaps = MutableLiveData(false)
    var keyboardType = MutableLiveData(KeyboardType.Normal)
    var isNumberSymbol = MutableLiveData(false)
    var actionButtonText = MutableLiveData("return")
    var actionButtonColor = MutableLiveData(colors.background)
    var actionTextColor = MutableLiveData(colors.primaryVariant)
    var isCapsLock = MutableLiveData(false)
    var keyboardSize = MutableLiveData(IntOffset(screenWidth, 275 + 50))
    var wordsList = MutableLiveData(first5kWords)
    var emojiDataDao: RecentEmojiDatabaseDAO? = null
    var dataB: EmojisDatabase? = null
    val isPhoneSymbol = MutableLiveData(false)


    private fun playBeep( m: MediaPlayer): Unit {
        try {
            if (m.isPlaying) m.stop()

            m.prepare()
            m.start().let { m.release() }
        } catch ( e: Exception) {
            e.printStackTrace()
        }
    }

    fun onEmojiClick(context: Context, emoji: String, scope: CoroutineScope) {
        scope.launch {
            val connection = (context as IMEService).currentInputConnection
            connection.commitText(emoji, emoji.length)


            val data = emojiDataDao?.getEmojisById(emoji)
            val all = emojiDataDao?.getAllEmojis()?.value

            if(data != null && data.id == emoji) {
                emojiDataDao?.delete(data).let {
                    emojiDataDao?.insert(data)
                }
            }

            if(data != null && all != null && all.size >= 12) {
                val last: EmojiData = all.last()
                emojiDataDao?.delete(last).let {
                    emojiDataDao?.insert(EmojiData(id = emoji))
                }
            } else emojiDataDao?.insert(EmojiData(id = emoji))
        }
    }

    fun onTKeyClick(
        haptic: HapticFeedback,
        key: Key, ctx: Context
    ) {
        val connection = (ctx as IMEService).currentInputConnection

        when (key.id) {
            "ABC" -> keyboardType.value = KeyboardType.Normal

            "123" -> keyboardType.value = KeyboardType.Symbol

            "action" -> ctx.sendDefaultEditorAction(false)
            
            "space" -> {
                val textBeforeCursor = connection.getTextBeforeCursor(1, GET_TEXT_WITH_STYLES)
                if (textBeforeCursor.toString() == " ") {
                    connection.deleteSurroundingText(1, 0).let {
                        connection.commitText(". ", ". ".length)
                    }
                } else connection.commitText(" ", " ".length)
            }

            else -> connection.commitText(
                (if (isAllCaps.value as Boolean) {
                    key.value.uppercase(Locale.getDefault())
                } else key.value.lowercase(Locale.getDefault())), key.value.length
            )
        }

        //Handle Caps State
        if(isAllCaps.value == true && isCapsLock.value == false) isAllCaps.value = false
        //Play Sound
//        playBeep(m)
        //Handle HapticFeedback
//        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    fun onNumKeyClick(
        haptic: HapticFeedback,
        key: Key, ctx: Context
    ) {
        val connection = (ctx as IMEService).currentInputConnection

        if(key.id != "shift") {
            connection.commitText(key.id, key.id.length)
        }

        //Play Sound
//        playBeep(m)
        //Handle HapticFeedback
//        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    fun onPhoneSymbol(
        haptic: HapticFeedback,
        key: Key, ctx: Context
    ) {
        isPhoneSymbol.value = !(isPhoneSymbol.value)!!
        //Play Sound
//        playBeep(m)
        // it is safe to cancel other vibrations currently taking place
//        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    fun onIKeyClick(
        haptic: HapticFeedback,
        key: Key, ctx: Context
    ) {
        val connection = (ctx as IMEService).currentInputConnection

        when (key.id) {
            "erase" -> connection.deleteSurroundingText(1, 67)

            "shift" -> {
                if(isCapsLock.value == true) {
                    isCapsLock.value = false
                    isAllCaps.value = false
                } else isAllCaps.value = !(isAllCaps.value)!!
            }

            "symbol" -> isNumberSymbol.value = !(isNumberSymbol.value)!!
        }
        //Play Sound
//        playBeep(m)
        // it is safe to cancel other vibrations currently taking place
//        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    fun onIDoubleClick(haptic: HapticFeedback, key: Key
    ) {
        if ("shift" == key.id) {
            isAllCaps.value = true
            isCapsLock.value = true
        }

//        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }
}
