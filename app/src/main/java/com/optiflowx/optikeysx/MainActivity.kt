package com.optiflowx.optikeysx

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import com.optiflowx.optikeysx.screens.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            enableEdgeToEdge()

            CupertinoTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
                isSystemInDarkTheme()
            }
        }
    }
}