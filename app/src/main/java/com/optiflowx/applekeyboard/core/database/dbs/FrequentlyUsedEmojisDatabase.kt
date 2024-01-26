package com.optiflowx.applekeyboard.core.database.dbs

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.room.Database
import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
import com.optiflowx.applekeyboard.core.database.dao.FrequentlyUsedEmojiDatabaseDAO
import com.optiflowx.applekeyboard.core.database.entities.FrequentlyUsedEmoji

@Immutable
//@TypeConverters(Converters::class)
@Database(entities = [FrequentlyUsedEmoji::class], version = 1, exportSchema = false)
abstract class FrequentlyUsedDatabase : RoomDatabase() {
    abstract fun fUsedEmojiDatabaseDAO(): FrequentlyUsedEmojiDatabaseDAO

    companion object {
        @Volatile
        private var INSTANCE: FrequentlyUsedDatabase? = null

        fun getInstance(context: Context): FrequentlyUsedDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = androidx.room.Room.databaseBuilder(
                        context.applicationContext,
                        FrequentlyUsedDatabase::class.java,
                        "Frequently_Used_Emoji_Database"
                    ).build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}