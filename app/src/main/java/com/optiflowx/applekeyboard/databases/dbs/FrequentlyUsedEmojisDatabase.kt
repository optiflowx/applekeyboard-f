package com.optiflowx.applekeyboard.databases.dbs

import androidx.compose.runtime.Immutable
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.optiflowx.applekeyboard.databases.dao.FrequentlyUsedEmojiDatabaseDAO
import com.optiflowx.applekeyboard.databases.entities.FrequentlyUsedEmoji
import com.optiflowx.applekeyboard.utils.Converters


@Database(entities = [FrequentlyUsedEmoji::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
@Immutable
abstract class FrequentlyUsedDatabase : RoomDatabase() {
    abstract fun fUsedEmojiDatabaseDAO(): FrequentlyUsedEmojiDatabaseDAO
}