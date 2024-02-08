package com.optiflowx.optikeysx.ui.cupertino

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import splitties.systemservices.inputMethodManager

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun GrantPermissionUI(
    mic: State<Boolean>,
    ime: State<Boolean>,
    kSS: State<Boolean>,
    nP: State<Boolean>,
    activity: Activity,
) {
    val PERMISSIONS_REQUEST_RECORD_AUDIO = 1
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        CupertinoButton(onClick = {
            ActivityCompat.requestPermissions(
                activity, arrayOf(
                    Manifest.permission.RECORD_AUDIO
                ), PERMISSIONS_REQUEST_RECORD_AUDIO
            )
        }, enabled = !mic.value) {
            if (mic.value) {
                CupertinoText("Microphone Permission Granted")
            } else {
                CupertinoText("Microphone Permission Not Granted")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        CupertinoButton(onClick = {
            context.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }, enabled = !ime.value) {
            if (ime.value) {
                CupertinoText("Keyboard Enabled")
            } else {
                CupertinoText("Keyboard Not Enabled")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        CupertinoButton(onClick = {
            inputMethodManager.showInputMethodPicker()
        }, enabled = !kSS.value) {
            if (kSS.value) {
                CupertinoText("Keyboard Selected")
            } else {
                CupertinoText("Keyboard Not Selected")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        CupertinoButton(onClick = {
            inputMethodManager.showInputMethodPicker()
        }, enabled = !nP.value) {
            if (nP.value) {
                CupertinoText("Notifications Permission Granted")
            } else {
                CupertinoText("Notifications Permission Not Granted")
            }
        }
    }
}