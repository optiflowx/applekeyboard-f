package com.optiflowx.optikeysx.ime.managers

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.ExtractedTextRequest
import com.optiflowx.optikeysx.ime.services.IMEService

class ActionManager(private val ime: IMEService) {
    private var selectionStart = 0
    private var selectionEnd = 0

    fun onCreateInputView() {
        val ic = ime.currentInputConnection
        if (ic != null) {
            val et = ic.getExtractedText(ExtractedTextRequest(), 0)
            if (et != null) {
                selectionStart = et.selectionStart
                selectionEnd = et.selectionEnd
            } else {
                selectionStart = 0
                selectionEnd = 0
            }
        }
    }

    fun updateSelection(
        newSelStart: Int,
        newSelEnd: Int,
    ) {
        selectionStart = newSelStart
        selectionEnd = newSelEnd
    }

    fun selectCharsBack(chars: Int) {
        val ic = ime.currentInputConnection ?: return
        var start = selectionEnd - chars
        if (start < 0) start = 0
        ic.setSelection(start, selectionEnd)
    }

    fun deleteSelection() {
        val ic = ime.currentInputConnection ?: return
        ic.commitText("", 1)
    }

    fun deleteLastChar() {
        // delete last char
        val ic = ime.currentInputConnection ?: return
        val selectedChars = ic.getSelectedText(0)
        if (selectedChars == null) {
            ic.deleteSurroundingText(1, 0)
        } else if (selectedChars.toString().isEmpty()) {
            ic.deleteSurroundingText(1, 0)
        } else {
            ic.performContextMenuAction(android.R.id.cut)
        }
    }

    fun sendEnter() {
        val ic = ime.currentInputConnection ?: return
        if (ime.keyboardData.enterAction == EditorInfo.IME_ACTION_UNSPECIFIED) {
            if (ime.isRichTextEditor) {
                ic.commitText("\n", 1)
            } else {
                ime.sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER)
            }
        } else {
            ic.performEditorAction(ime.keyboardData.enterAction)
        }
    }

    companion object {
        private const val TAG = "ActionManager"
    }
}