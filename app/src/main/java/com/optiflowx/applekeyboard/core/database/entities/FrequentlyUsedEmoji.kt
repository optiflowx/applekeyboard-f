package com.optiflowx.applekeyboard.core.database.entities

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Immutable
@Entity(tableName = "Frequently_Used_Emoji_Database")
data class FrequentlyUsedEmoji(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,

    @ColumnInfo(name = "emoji")
    val emoji: String
)