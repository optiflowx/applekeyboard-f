package com.optiflowx.optikeysx.ime.recognizers.providers

import android.content.Context
import com.optiflowx.optikeysx.core.Tools
import com.optiflowx.optikeysx.core.data.InstalledModelReference
import com.optiflowx.optikeysx.core.data.ModelType
import com.optiflowx.optikeysx.ime.recognizers.RecognizerSource

class Providers(context: Context) {
    private val voskLocalProvider: VoskLocalProvider
    private val providers: List<RecognizerSourceProvider>

    init {
        val providersM = mutableListOf<RecognizerSourceProvider>()
        voskLocalProvider = VoskLocalProvider(context)
        providersM.add(voskLocalProvider)
        if (Tools.VOSK_SERVER_ENABLED) {
            providersM.add(VoskServerProvider())
        }
        providers = providersM
    }

    fun recognizerSourceForModel(localModel: InstalledModelReference): RecognizerSource? {
        return when (localModel.type) {
            ModelType.VoskLocal -> voskLocalProvider.recognizerSourceForModel(localModel)
        }
    }

    fun installedModels(): Collection<InstalledModelReference> {
        return providers.map { it.getInstalledModels() }.flatten()
    }
}