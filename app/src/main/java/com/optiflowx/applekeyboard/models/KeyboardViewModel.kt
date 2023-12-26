package com.optiflowx.applekeyboard.models

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.inputmethod.InputConnection.GET_TEXT_WITH_STYLES
import androidx.compose.material.Colors
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.adapters.Key
import com.optiflowx.applekeyboard.database.EmojisDatabase
import com.optiflowx.applekeyboard.database.dao.RecentEmojiDatabaseDAO
import com.optiflowx.applekeyboard.database.entities.EmojiData
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.utils.KeyboardType
import com.optiflowx.applekeyboard.utils.first5kWords
import kotlinx.coroutines.launch
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
    val bottomPaddingValue = MutableLiveData(0.dp)

    private fun playBeep(id: Int, ctx: Context) {
        val m = MediaPlayer.create(ctx, id)
        m.start()
    }

    fun onEmojiClick(context: Context, emoji: String, title: String) {
        viewModelScope.launch {
            val connection = (context as IMEService).currentInputConnection
            connection.commitText(emoji, emoji.length)

            if(!title.lowercase().contains("frequently")) {
                val id = emoji.codePointAt(0)
                val data = emojiDataDao?.getEmojisById(id)
                val all = emojiDataDao?.getAllEmojis()?.value

                if (data != null && data.emoji == emoji) {
                    emojiDataDao?.delete(data).let {
                        emojiDataDao?.insert(data)
                    }
                } else if (data != null && all != null && all.size == 18) {
                    val last: EmojiData = all.last()
                    emojiDataDao?.delete(last).let {
                        emojiDataDao?.insert(EmojiData(id = id,emoji = emoji))
                    }
                } else emojiDataDao?.insert(EmojiData(id = id,emoji = emoji))
            }
        }
    }

    fun onTKeyClick(
        key: Key, ctx: Context
    ) {
        viewModelScope.launch {
            val connection = (ctx as IMEService).currentInputConnection

            when (key.id) {
                "ABC" -> {
                    keyboardType.value = KeyboardType.Normal
                    playBeep(R.raw.standard, ctx)
                }

                "123" -> {
                    keyboardType.value = KeyboardType.Symbol
                    playBeep(R.raw.standard, ctx)
                }

                "action" -> {
                    ctx.sendDefaultEditorAction(false)
                    playBeep(R.raw.ret, ctx)
                }

                "space" -> {
                    val textBeforeCursor = connection.getTextBeforeCursor(1, GET_TEXT_WITH_STYLES)
                    if (textBeforeCursor.toString() == " ") {
                        connection.deleteSurroundingText(1, 0).let {
                            connection.commitText(". ", ". ".length)
                        }
                    } else connection.commitText(" ", " ".length)
                    playBeep(R.raw.spacebar, ctx)
                }

                else -> {
                    connection.commitText(
                        (if (isAllCaps.value as Boolean) {
                            key.value.uppercase(Locale.getDefault())
                        } else key.value.lowercase(Locale.getDefault())), key.value.length
                    )
                    playBeep(R.raw.standard, ctx)
                }
            }
            //Handle Caps State
            if (isAllCaps.value == true && isCapsLock.value == false) isAllCaps.value = false
        }
    }

    fun onNumKeyClick(key: Key, ctx: Context) {
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
            playBeep(R.raw.standard, ctx)
        }
    }

    fun onPhoneSymbol() {
        viewModelScope.launch {
            isPhoneSymbol.value = !(isPhoneSymbol.value)!!
        }
    }

    fun onIKeyClick(key: Key, ctx: Context) {
        viewModelScope.launch {
            val connection = (ctx as IMEService).currentInputConnection
            val text = connection.getTextBeforeCursor(20, 0)

            when (key.id) {
                "erase" -> {
                    if(text?.codePoints() != null) {
                        val codePoints = text.codePoints().toArray()
                        if (codePoints.isNotEmpty()) {
                            connection.deleteSurroundingTextInCodePoints(1, 0)
                        }
                    } else connection.deleteSurroundingText(1, 0)
                    playBeep(R.raw.delete, ctx)
                }

                "shift" -> {
                    if (isCapsLock.value == true) {
                        isCapsLock.value = false
                        isAllCaps.value = false
                    } else isAllCaps.value = !(isAllCaps.value)!!
                    playBeep(R.raw.standard, ctx)
                }

                "symbol" -> {
                    isNumberSymbol.value = !(isNumberSymbol.value)!!
                    playBeep(R.raw.standard, ctx)
                }
            }
        }
    }

    fun onIDoubleClick(key: Key) {
        viewModelScope.launch {
            if ("shift" == key.id) {
                isAllCaps.value = true
                isCapsLock.value = true
            }
        }
    }
}
