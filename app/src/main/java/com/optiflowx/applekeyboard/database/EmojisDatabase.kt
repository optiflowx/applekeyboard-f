package com.optiflowx.applekeyboard.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.optiflowx.applekeyboard.database.dao.RecentEmojiDatabaseDAO
import com.optiflowx.applekeyboard.database.entities.EmojiData
import com.optiflowx.applekeyboard.utils.Converters


@Database(entities = [EmojiData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EmojisDatabase : RoomDatabase() {
    abstract fun appDataDAO(): RecentEmojiDatabaseDAO
}