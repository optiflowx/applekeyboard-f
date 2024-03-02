package com.optiflowx.optikeysx.ime.recognizers.providers

import com.optiflowx.optikeysx.core.data.InstalledModelReference
import com.optiflowx.optikeysx.ime.recognizers.RecognizerSource

interface RecognizerSourceProvider {
    fun getInstalledModels(): Collection<InstalledModelReference>

    fun recognizerSourceForModel(localModel: InstalledModelReference): RecognizerSource?
}