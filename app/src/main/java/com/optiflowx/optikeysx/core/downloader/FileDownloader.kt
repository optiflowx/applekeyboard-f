package com.optiflowx.optikeysx.core.downloader

import android.content.Context
import android.content.Intent
import android.os.Build
import com.optiflowx.optikeysx.core.data.ModelLink
import com.optiflowx.optikeysx.core.downloader.FileDownloadService
import com.optiflowx.optikeysx.core.downloader.messages.ModelInfo
import java.util.Locale

object FileDownloader {
    const val ACTION = "action"
    const val ACTION_DOWNLOAD = "action_download"
    const val ACTION_UNZIP = "action_unzip"

    private const val DOWNLOAD_URL = "download_url"
    private const val DOWNLOAD_FILENAME = "download_filename"
    private const val DOWNLOAD_LOCALE = "download_locale"

    const val UNZIP_URI = "unzip_uri"
    const val UNZIP_LOCALE = "unzip_locale"
    fun getInfoForIntent(intent: Intent): ModelInfo? {
        val url = intent.getStringExtra(DOWNLOAD_URL)
        val filename = intent.getStringExtra(DOWNLOAD_FILENAME)
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(DOWNLOAD_LOCALE, Locale::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(DOWNLOAD_LOCALE) as Locale?
        }
        return if (url == null || filename == null || locale == null) null else ModelInfo(
            url,
            filename,
            locale
        )
    }

    fun downloadModel(model: ModelLink, context: Context) {
        val ctx = context.applicationContext
        val serviceIntent = Intent(ctx, FileDownloadService::class.java)
        serviceIntent.putExtra(ACTION, ACTION_DOWNLOAD)
        serviceIntent.putExtra(DOWNLOAD_URL, model.link)
        serviceIntent.putExtra(DOWNLOAD_FILENAME, model.filename)
        serviceIntent.putExtra(DOWNLOAD_LOCALE, model.locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ctx.startForegroundService(serviceIntent)
        } else ctx.startService(serviceIntent)
    }
}