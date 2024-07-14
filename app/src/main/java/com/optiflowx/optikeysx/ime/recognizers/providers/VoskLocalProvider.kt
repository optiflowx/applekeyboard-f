package com.optiflowx.optikeysx.ime.recognizers.providers

import android.content.Context
import com.optiflowx.optikeysx.core.Tools
import com.optiflowx.optikeysx.core.data.InstalledModelReference
import com.optiflowx.optikeysx.core.data.ModelType
import com.optiflowx.optikeysx.extensions.DownloadsExtensions
import com.optiflowx.optikeysx.ime.recognizers.RecognizerSource
import com.optiflowx.optikeysx.ime.recognizers.VoskLocal
import java.util.Locale

class VoskLocalProvider(private val context: Context) : RecognizerSourceProvider {
    override fun getInstalledModels(): List<InstalledModelReference> {
        val models: MutableList<InstalledModelReference> = ArrayList()
        val modelsDir = DownloadsExtensions.getModelsDirectory(context)
        if (!modelsDir.exists()) return models
        for (localeFolder in modelsDir.listFiles()!!) {
            if (!localeFolder.isDirectory) continue
            val locale = Locale.forLanguageTag(localeFolder.name)
            for (modelFolder in localeFolder.listFiles()!!) {
                if (!modelFolder.isDirectory) continue
                val model = InstalledModelReference(
                    modelFolder.absolutePath,
                    locale.displayName,
                    ModelType.VoskLocal
                )
                models.add(model)
            }
        }
        return models
    }

    override fun recognizerSourceForModel(localModel: InstalledModelReference): RecognizerSource? {
        return VoskLocal(Tools.getVoskModelFromReference(localModel) ?: return null)
    }
}