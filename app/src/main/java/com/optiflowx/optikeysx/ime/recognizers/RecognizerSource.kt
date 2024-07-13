package com.optiflowx.optikeysx.ime.recognizers

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import com.optiflowx.optikeysx.core.enums.RecognizerState
import java.util.concurrent.Executor

interface RecognizerSource {
    fun initialize(executor: Executor)
    val recognizer: Recognizer
    fun close(freeRAM: Boolean)
    val stateLD: LiveData<RecognizerState>

    val addSpaces: Boolean

    val closed: Boolean

    @get:StringRes
    val errorMessage: Int
    val name: String
}