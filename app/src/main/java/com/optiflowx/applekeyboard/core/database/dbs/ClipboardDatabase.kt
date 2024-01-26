package com.optiflowx.applekeyboard.core.database.dbs

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.room.Database
import androidx.room.RoomDatabase
import com.optiflowx.applekeyboard.core.database.dao.ClipboardDatabaseDAO
import com.optiflowx.applekeyboard.core.database.entities.ClipData

@Immutable
@Database(entities = [ClipData::class], version = 2, exportSchema = false)
abstract class ClipboardDatabase : RoomDatabase() {
    abstract fun clipboardDAO(): ClipboardDatabaseDAO

    companion object {
        @Volatile
        private var INSTANCE: ClipboardDatabase? = null

        fun getInstance(context: Context): ClipboardDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = androidx.room.Room.databaseBuilder(
                        context.applicationContext,
                        ClipboardDatabase::class.java,
                        "Clipboard_Database"
                    ).build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}