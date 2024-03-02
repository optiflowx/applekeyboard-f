package com.optiflowx.optikeysx.core.data

import android.os.IBinder
import android.view.inputmethod.EditorInfo

data class KeyboardData(
    val locale: String = "",
    val enterAction: Int = EditorInfo.IME_ACTION_UNSPECIFIED,
    val token: IBinder? = null,
    val inputType: Int = 0,

    )
