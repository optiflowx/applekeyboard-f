package com.optiflowx.optikeysx

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.core.app.ActivityCompat
import cafe.adriel.voyager.navigator.Navigator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.optiflowx.optikeysx.core.Tools
import com.optiflowx.optikeysx.extension.getAudioPermission
import com.optiflowx.optikeysx.extension.getNotificationPermission
import com.optiflowx.optikeysx.screens.home.HomeScreen
import com.optiflowx.optikeysx.screens.permissions.PermissionsScreen
import com.optiflowx.optikeysx.screens.sso.SignInScreen
import dev.patrickgold.jetpref.datastore.model.observeAsState
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val micGranted = MutableStateFlow(false)
    private val imeGranted = MutableStateFlow(false)
    private val notificationsGranted = MutableStateFlow(false)
    private val permissionsState = MutableStateFlow(0)
    private val prefs by optikeysxPreferences()

    private fun onPermissionButtonClick(state: State<Int>) {
        when (state.value) {
            0 -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(getAudioPermission()),
                    1
                )
            }

            1 -> ActivityCompat.requestPermissions(
                this,
                arrayOf(getNotificationPermission()),
                2
            )

            2 -> this.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }
    }

    private fun checkPermissions() {
        micGranted.value = Tools.isMicrophonePermissionGranted(this)
        notificationsGranted.value = Tools.isNotificationAccessGranted(this)
        imeGranted.value = Tools.isIMEEnabled(this)

        if (!micGranted.value) {
            permissionsState.value = 0
        } else if (micGranted.value && !notificationsGranted.value) {
            permissionsState.value = 1
        } else if (micGranted.value && notificationsGranted.value && !imeGranted.value) {
            permissionsState.value = 2
        } else if (micGranted.value && imeGranted.value && notificationsGranted.value) {
            permissionsState.value = 3
        }
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

        auth = Firebase.auth

        Tools.createNotificationChannel(this)

        checkPermissions()

        enableEdgeToEdge()

        setContent {
            CupertinoTheme {
                val isAuth = prefs.isAuthenticated.observeAsState().value
                val micGrantedState = micGranted.collectAsState()
                val imeGrantedState = imeGranted.collectAsState()
                val notificationsGrantedState = notificationsGranted.collectAsState()
                val state = permissionsState.collectAsState()

                isSystemInDarkTheme()

                if (micGrantedState.value && imeGrantedState.value &&
                    notificationsGrantedState.value && state.value == 3
                ) {
                    if (isAuth) {
//                        if (user.isEmailVerified) {
                        Navigator(HomeScreen())
//                        } else VerifyEmailScreen()
                    } else Navigator(SignInScreen())
                } else PermissionsScreen(
                    micGranted = micGrantedState,
                    imeGranted = imeGrantedState,
                    notificationsGranted = notificationsGrantedState,
                    state = state,
                    onClick = { onPermissionButtonClick(state) }
                )
            }
        }
    }
}