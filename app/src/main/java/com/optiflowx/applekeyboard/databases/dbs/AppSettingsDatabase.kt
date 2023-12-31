package com.optiflowx.applekeyboard.databases.dbs
//
//import android.content.Context
//import androidx.compose.runtime.Immutable
//import androidx.room.Database
//import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
//import com.optiflowx.applekeyboard.databases.dao.AppSettingsDatabaseDAO
//import com.optiflowx.applekeyboard.databases.entities.AppSetting
//import com.optiflowx.applekeyboard.utils.Converters
//
//
//@Immutable
//@TypeConverters(Converters::class)
//@Database(entities = [AppSetting::class], version = 1, exportSchema = false)
//abstract class AppSettingsDatabase : RoomDatabase() {
//    abstract fun appSettingDatabaseDAO(): AppSettingsDatabaseDAO
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppSettingsDatabase? = null
//
//        fun getInstance(context: Context): AppSettingsDatabase {
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = androidx.room.Room.databaseBuilder(
//                        context.applicationContext,
//                        AppSettingsDatabase::class.java,
//                        "App_Settings_Database"
//                    ).build()
//                    INSTANCE = instance
//                }
//
//                return instance
//            }
//        }
//    }
//}