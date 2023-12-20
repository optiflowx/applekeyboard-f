package com.optiflowx.applekeyboard.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.optiflowx.applekeyboard.database.dao.AppDatabaseDAO
import com.optiflowx.applekeyboard.database.entities.AppData

@Database(entities = [AppData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDataDAO(): AppDatabaseDAO
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "optiflowx_keyboard_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}