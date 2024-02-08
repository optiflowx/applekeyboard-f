package com.optiflowx.optikeysx.core.downloader.messages

import java.util.Queue

data class Status(
    val current: ModelInfo?,
    val queued: Queue<ModelInfo>,
    val downloadProgress: Float,
    val unzipProgress: Float,
    val state: State
)