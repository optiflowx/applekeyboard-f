package com.optiflowx.optikeysx.ime

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.ExtractedTextRequest
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.optiflowx.optikeysx.MainActivity

class ActionManager(private val ime: IMEService) {
    private val mInputMethodManager: InputMethodManager =
        ime.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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

    fun switchToLastIme(showError: Boolean) {
        val result: Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ime.switchToPreviousInputMethod()
        } else {
            mInputMethodManager.switchToLastInputMethod(ime.token)
        }
        if (!result && showError) {
            Toast.makeText(ime, "There is no previous ime", Toast.LENGTH_SHORT).show()
        }
    }

    fun openSettings() {
        val myIntent = Intent(ime, MainActivity::class.java)
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ime.startActivity(myIntent)
    }

    companion object {
        private const val TAG = "ActionManager"
    }
}