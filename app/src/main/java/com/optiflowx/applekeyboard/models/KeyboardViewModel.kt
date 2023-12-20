package com.optiflowx.applekeyboard.models

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.material.Colors
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.optiflowx.applekeyboard.adapters.Key
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.utils.KeyboardType
import com.optiflowx.applekeyboard.utils.first5kWords
import kotlinx.coroutines.CoroutineScope
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
//    var appSettings = MutableLiveData(AppSettings())



    private fun playBeep( m: MediaPlayer): Unit {
        try {
            if (m.isPlaying) m.stop()

            m.prepare()
            m.start().let { m.release() }
        } catch ( e: Exception) {
            e.printStackTrace()
        }
    }

    fun onEmojiClick(context: Context, emoji: String,scope: CoroutineScope) {
        val connection = (context as IMEService).currentInputConnection
        scope.launch {
//            dataStore.saveEmail(email)

            connection.commitText(emoji, emoji.length)
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
                val textBeforeCursor = connection.getTextBeforeCursor(1, 0)
                if (textBeforeCursor.toString() == " ") {
                    connection.deleteSurroundingText(1, 67).let {
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
