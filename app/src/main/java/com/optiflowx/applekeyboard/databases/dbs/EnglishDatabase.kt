package com.optiflowx.applekeyboard.databases.dbs

import androidx.compose.runtime.Immutable
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.optiflowx.applekeyboard.databases.dao.EnglishDatabaseDAO
import com.optiflowx.applekeyboard.databases.entities.EnglishWord
import com.optiflowx.applekeyboard.utils.Converters


@Database(entities = [EnglishWord::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
@Immutable
abstract class EnglishDatabase : RoomDatabase() {
    abstract fun englishDatabaseDAO(): EnglishDatabaseDAO
}