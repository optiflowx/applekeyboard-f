package com.optiflowx.applekeyboard.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
@Entity(tableName = "app_data")
data class AppData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "is_dark_mode")
    val isDarkMode: Boolean = false,

    @ColumnInfo(name = "is_haptic_feedback")
    val isHapticFeedback: Boolean = false,

    @ColumnInfo(name = "is_sound_feedback")
    val isSoundFeedback: Boolean = false,

    @ColumnInfo(name = "is_auto_capitalize")
    val isAutoCapitalize: Boolean = false,

    @ColumnInfo(name = "is_auto_correct")
    val isAutoCorrect: Boolean = false,

//    @ColumnInfo(name = "is_auto_complete")
//    val isAutoComplete: Boolean = false,


    @ColumnInfo(name = "freq_used_emojis")
    val frequentlyUsedEmojis: PersistentList<String> = persistentListOf(),
)