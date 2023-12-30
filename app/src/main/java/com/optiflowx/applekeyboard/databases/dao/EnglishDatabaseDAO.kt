package com.optiflowx.applekeyboard.databases.dao

import androidx.compose.runtime.Immutable
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.optiflowx.applekeyboard.databases.entities.EnglishWord

@Dao
@Immutable
interface EnglishDatabaseDAO {
    @Query("SELECT * FROM English_Database")
    fun getWords(): LiveData<List<EnglishWord>>

    @Insert
    suspend fun insert(word: EnglishWord)

    @Delete
    suspend fun delete(word: EnglishWord)
}