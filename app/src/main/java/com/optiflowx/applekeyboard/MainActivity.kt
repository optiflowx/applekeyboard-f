package com.optiflowx.applekeyboard

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import com.optiflowx.applekeyboard.screens.NavGraphs
import com.optiflowx.applekeyboard.ui.AppleKeyboardIMETheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : AppCompatActivity() {
//    private var vm: AppViewModel? = null

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
//        if(vm == null) vm = AppViewModel(this)
    }

    override fun onDestroy() {
        super.onDestroy()
//        vm = null
    }
}