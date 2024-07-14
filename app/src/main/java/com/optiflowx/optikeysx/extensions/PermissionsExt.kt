package com.optiflowx.optikeysx.extensions

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Stable

/**
 * Check for android 13 (TIRAMISU) permissions
 */

@Stable
fun getReadStoragePermission() = when {
    Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ->
        Manifest.permission.READ_EXTERNAL_STORAGE
    else -> Manifest.permission.READ_MEDIA_AUDIO
}

@Stable
fun getNotificationPermission() = when {
    Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ->
        Manifest.permission.ACCESS_NOTIFICATION_POLICY
    else -> Manifest.permission.POST_NOTIFICATIONS
}

@Stable
fun getAudioPermission() = Manifest.permission.RECORD_AUDIO
