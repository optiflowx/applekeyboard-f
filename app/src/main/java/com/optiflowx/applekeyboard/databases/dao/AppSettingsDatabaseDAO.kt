package com.optiflowx.applekeyboard.databases.dao
//
//import androidx.compose.runtime.Immutable
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Update
//import com.optiflowx.applekeyboard.databases.entities.AppSetting
//
//@Dao
//@Immutable
//interface AppSettingsDatabaseDAO {
//    @Query("SELECT * FROM App_Settings_Database WHERE id = 0")
//    fun getAppSettings(): LiveData<AppSetting>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(setting: AppSetting)
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun update(setting: AppSetting)
//
//    @Delete
//    suspend fun delete(setting: AppSetting)
//    //We should never delete the app database
//    //unless a user explicitly wants to delete all their app data
//}