package com.optiflowx.applekeyboard

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import com.optiflowx.applekeyboard.screens.NavGraphs
import com.optiflowx.applekeyboard.ui.AppleKeyboardIMETheme
import com.ramcosta.composedestinations.DestinationsNavHost

//@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppleKeyboardIMETheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
                isSystemInDarkTheme()
            }
        }
    }
}



