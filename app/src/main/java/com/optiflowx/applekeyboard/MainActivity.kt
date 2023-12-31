package com.optiflowx.applekeyboard

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import com.optiflowx.applekeyboard.screens.NavGraphs
import com.optiflowx.applekeyboard.ui.AppleKeyboardIMETheme
import com.optiflowx.applekeyboard.viewmodels.AppViewModel
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : AppCompatActivity() {
    private var appViewModel: AppViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppleKeyboardIMETheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
                isSystemInDarkTheme()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
        if (appViewModel == null) {
            appViewModel = AppViewModel(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
        appViewModel = null
    }
}