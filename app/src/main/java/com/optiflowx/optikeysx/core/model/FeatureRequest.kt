package com.optiflowx.optikeysx.core.model

import android.annotation.SuppressLint
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

class FeatureRequest {
    var content: String
    var status: String
    var time: String

    init {
        this.content = ""
        this.status = ""
        this.time = ""
    }

    @SuppressLint("SimpleDateFormat")
    fun fromMap(map: Map<String, Any>) {
        val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")

        this.content = map["content"] as String
        this.status = map["status"] as String
        this.time = sdf.format((map["time"] as Timestamp).toDate())
    }
}