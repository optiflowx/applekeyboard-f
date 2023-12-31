package com.optiflowx.applekeyboard.databases.dao

import androidx.compose.runtime.Immutable
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.optiflowx.applekeyboard.databases.entities.FrequentlyUsedEmoji

@Dao
@Immutable
interface FrequentlyUsedEmojiDatabaseDAO {
    @Query("SELECT * FROM Frequently_Used_Emoji_Database ORDER BY id ASC")
    fun getAllEmojis(): LiveData<List<FrequentlyUsedEmoji>>

    @Query("SELECT * FROM Frequently_Used_Emoji_Database WHERE id = :id")
    fun getEmojisById(id: Int): FrequentlyUsedEmoji?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(frequentlyUsedEmoji: FrequentlyUsedEmoji)

    @Delete
    suspend fun delete(frequentlyUsedEmoji: FrequentlyUsedEmoji)
}