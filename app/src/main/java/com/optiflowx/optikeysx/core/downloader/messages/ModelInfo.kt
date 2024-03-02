package com.optiflowx.optikeysx.core.downloader.messages

import java.util.Locale

data class ModelInfo(val url: String, val filename: String, val locale: Locale = Locale.ROOT) {
    override fun toString(): String {
        return "ModelInfo{" +
                "url='" + url + '\'' +
                ", filename='" + filename + '\'' +
                ", locale=" + locale +
                '}'
    }
}