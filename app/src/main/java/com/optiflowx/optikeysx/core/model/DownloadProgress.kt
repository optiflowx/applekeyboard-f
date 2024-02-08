package com.optiflowx.optikeysx.core.model

import androidx.compose.runtime.Immutable
import com.optiflowx.optikeysx.core.downloader.messages.ModelInfo
import com.optiflowx.optikeysx.core.downloader.messages.State

@Immutable
data class DownloadProgress(
    val info: ModelInfo, var state: State, var progress: Float
) {
    fun withProgress(newProgress: Float): DownloadProgress {
        return DownloadProgress(info, state, newProgress)
    }

    fun withState(newState: State): DownloadProgress {
        return DownloadProgress(info, newState, progress)
    }
}