package com.optiflowx.applekeyboard.databases.dao

import androidx.compose.runtime.Immutable
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.optiflowx.applekeyboard.databases.entities.FrequentlyUsedEmoji

@Dao
@Immutable
interface FrequentlyUsedEmojiDatabaseDAO {
    @Query("SELECT * FROM Frequently_Used_Emoji_Database")
    fun getAllEmojis(): LiveData<List<FrequentlyUsedEmoji>>

    @Query("SELECT * from Frequently_Used_Emoji_Database where id = :id")
    fun getEmojisById(id: Int): FrequentlyUsedEmoji?

    @Insert
    suspend fun insert(frequentlyUsedEmoji: FrequentlyUsedEmoji)

    @Delete
    suspend fun delete(frequentlyUsedEmoji: FrequentlyUsedEmoji)
}