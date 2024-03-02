package com.optiflowx.optikeysx.core.enums

import androidx.compose.runtime.Immutable

@Immutable
enum class RecognitionState {
    INITIAL,
    LOADING,
    READY,
    LISTENING,
    PAUSED,
    RECOGNIZER_ERROR,
    NO_MODEL,
    NO_MIC_PERMISSION,
}