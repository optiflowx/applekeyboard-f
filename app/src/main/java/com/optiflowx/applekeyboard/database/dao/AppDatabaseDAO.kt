package com.optiflowx.applekeyboard.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.optiflowx.applekeyboard.database.entities.AppData
import kotlinx.collections.immutable.PersistentList

interface AppDatabaseDAO {
    @Query("SELECT * FROM app_data")
    fun getAll(): LiveData<List<AppData>>

    @Query("SELECT * FROM app_data WHERE id = :id")
    fun getById(id: Long): AppData?

    @Query("SELECT * FROM app_data WHERE is_dark_mode = :isDarkMode")
    fun getByIsDarkMode(isDarkMode: Boolean): AppData?

    @Query("SELECT * FROM app_data WHERE is_haptic_feedback = :isHapticFeedback")
    fun getByIsHapticFeedback(isHapticFeedback: Boolean): AppData?

    @Query("SELECT * FROM app_data WHERE is_sound_feedback = :isSoundFeedback")
    fun getByIsSoundFeedback(isSoundFeedback: Boolean): AppData?

    @Query("SELECT * FROM app_data WHERE is_auto_capitalize = :isAutoCapitalize")
    fun getByIsAutoCapitalize(isAutoCapitalize: Boolean): AppData?

    @Query("SELECT * FROM app_data WHERE is_auto_correct = :isAutoCorrect")
    fun getByIsAutoCorrect(isAutoCorrect: Boolean): AppData?

    @Query("SELECT * FROM app_data WHERE freq_used_emojis = :freqUsedEmojis")
    fun getByFreqUsedEmojis(freqUsedEmojis: PersistentList<String>): LiveData<AppData>

    @Insert
    suspend fun insert(appData: AppData)

    @Update
    suspend fun update(appData: AppData)
}