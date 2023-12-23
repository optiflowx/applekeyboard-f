package com.optiflowx.applekeyboard.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Frequently_Used_Emojis")
data class EmojiData(
    @PrimaryKey(autoGenerate = true)
    val key: Int = 0,

    @ColumnInfo(name = "id")
    val id: String
)