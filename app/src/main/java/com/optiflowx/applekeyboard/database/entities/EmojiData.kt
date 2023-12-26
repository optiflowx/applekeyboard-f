package com.optiflowx.applekeyboard.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Frequently_Used_Emojis")
data class EmojiData(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,

    @ColumnInfo(name = "emoji")
    val emoji: String
)