package com.optiflowx.optikeysx

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.navigator.Navigator
import com.optiflowx.optikeysx.core.Tools
import com.optiflowx.optikeysx.screens.home.HomeScreen
import com.optiflowx.optikeysx.ui.cupertino.GrantPermissionUI
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : AppCompatActivity() {

    private val micGranted = MutableStateFlow(false)
    private val imeGranted = MutableStateFlow(false)
    private val keyboardSelected = MutableStateFlow(true)
    private val notiGranted = MutableStateFlow(false)

    private fun checkPermissions() {
        micGranted.value = Tools.isMicrophonePermissionGranted(this)
        imeGranted.value = Tools.isIMEEnabled(this)
        notiGranted.value = Tools.isNotificationAccessGranted(this)
//        keyboardSelected.value = Tools.isKeyboardSelected(this)
    }

    override fun onResume() {
        super.onResume()
        checkPermissions()
    }

    override fun onPostResume() {
        super.onPostResume()
        checkPermissions()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Tools.createNotificationChannel(this)

        checkPermissions()

        this.enableEdgeToEdge()

        setContent {
            CupertinoTheme {
                val micGrantedState = micGranted.collectAsState()
                val imeGrantedState = imeGranted.collectAsState()
                val keyboardSelectedState = keyboardSelected.collectAsState()
                val notiGrantedState = notiGranted.collectAsState()

                isSystemInDarkTheme()

                if (micGrantedState.value && notiGrantedState.value && imeGrantedState.value && keyboardSelectedState.value) {
                    Navigator(HomeScreen(this))
                } else GrantPermissionUI(
                    mic = micGrantedState,
                    ime = imeGrantedState,
                    kSS = keyboardSelectedState,
                    nP = notiGrantedState,
                    activity = this,
                )
            }
        }
    }
}