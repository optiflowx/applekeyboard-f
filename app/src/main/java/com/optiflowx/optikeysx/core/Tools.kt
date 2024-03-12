package com.optiflowx.optikeysx.core

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.inputmethod.InputMethodManager
import androidx.compose.runtime.Immutable
import androidx.core.content.ContextCompat
import com.optiflowx.optikeysx.Constants
import com.optiflowx.optikeysx.core.data.InstalledModelReference
import com.optiflowx.optikeysx.core.data.VoskLocalModel
import com.optiflowx.optikeysx.extension.getAudioPermission
import com.optiflowx.optikeysx.extension.getNotificationPermission
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.Locale

@Immutable
object Tools {
    const val VOSK_SERVER_ENABLED = false

    @JvmStatic
    fun isMicrophonePermissionGranted(activity: Activity): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(
            activity.applicationContext,
            getAudioPermission()
        )
        return (permissionCheck == PackageManager.PERMISSION_GRANTED)
    }

    @JvmStatic
    fun isNotificationAccessGranted(activity: Activity): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(
            activity.applicationContext, getNotificationPermission()
        )

        return (permissionCheck == PackageManager.PERMISSION_GRANTED)
    }

    @JvmStatic
    fun isIMEEnabled(activity: Activity): Boolean {
        val imeManager = activity
            .applicationContext
            .getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager

        for (i in imeManager.enabledInputMethodList) {
            if (i.packageName == activity.packageName) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun deleteModel(model: InstalledModelReference) {
        val modelFile = File(model.path)
        if (modelFile.exists()) deleteRecursive(modelFile)
    }

    @JvmStatic
    fun deleteRecursive(fileOrDirectory: File, deleteStartingFolder: Boolean = true) {
        if (fileOrDirectory.isDirectory) for (child in fileOrDirectory.listFiles()!!)
            deleteRecursive(child, true)
        if (deleteStartingFolder) {
            fileOrDirectory.delete()
        }
    }

    fun getVoskModelFromReference(
        reference: InstalledModelReference
    ): VoskLocalModel? {
        val localeFolder = File(reference.path).parentFile ?: return null
        val locale = Locale.forLanguageTag(localeFolder.name)
        for (modelFolder in localeFolder.listFiles()!!) {
            if (!modelFolder.isDirectory) continue
            val name = modelFolder.name
            return VoskLocalModel(modelFolder.absolutePath, locale, name)
        }
        return null
    }

    fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Speech Recognition Model Download"
            val description = "Speech Recognition Model Download Notifications"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(Constants.DOWNLOADER_CHANNEL_ID, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        if (!outputFile.parentFile!!.exists()) {
            outputFile.parentFile!!.mkdirs()
        }
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }
}