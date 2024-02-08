package com.optiflowx.optikeysx.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import cafe.adriel.voyager.core.model.ScreenModel
import com.optiflowx.optikeysx.core.downloader.messages.ModelInfo
import com.optiflowx.optikeysx.core.model.DownloadProgress
import com.optiflowx.optikeysx.ime.recognizers.providers.Providers
import com.optiflowx.optikeysx.optikeysxPreferences
import kotlinx.coroutines.flow.MutableStateFlow


class KeyboardSettingsModel : ScreenModel {
    val prefs by optikeysxPreferences()

    val modelsPendingDownloadLD = MutableStateFlow<List<ModelInfo>>(mutableListOf())
    private val modelsPendingDownload = mutableListOf<ModelInfo>()
    val currentDownloadingModel = MutableStateFlow<DownloadProgress?>(null)

    private lateinit var recognizerSourceProviders: Providers

    fun initRecognizerSourceProviders(activity: Activity) {
        recognizerSourceProviders = Providers(activity)
    }

    fun reloadModels() {
        val currentModels = prefs.modelsOrder.get().toMutableList()
        val installedModels = recognizerSourceProviders.installedModels()
        currentModels.removeAll { it !in installedModels }
        for (model in installedModels) {
            if (model !in currentModels) {
                currentModels.add(model)
            }
        }

        prefs.modelsOrder.set(currentModels)
    }

    fun onAddKeyboard(context: Context) {
        val imId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.DEFAULT_INPUT_METHOD
        )

        val intent = Intent(Settings.ACTION_INPUT_METHOD_SUBTYPE_SETTINGS)
            .putExtra(Settings.EXTRA_INPUT_METHOD_ID, imId)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .putExtra(Intent.EXTRA_TITLE, "Add Keyboard")
        context.startActivity(intent);
    }

    override fun onDispose() {
        super.onDispose()
    }
}