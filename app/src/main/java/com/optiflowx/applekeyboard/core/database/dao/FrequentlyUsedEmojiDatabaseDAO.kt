package com.optiflowx.applekeyboard.core.database.dao

import androidx.compose.runtime.Immutable
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.optiflowx.applekeyboard.core.database.entities.FrequentlyUsedEmoji

@Dao
@Immutable
interface FrequentlyUsedEmojiDatabaseDAO {
    @Query("SELECT * FROM Frequently_Used_Emoji_Database ORDER BY id ASC")
    fun getAllEmojis(): LiveData<List<FrequentlyUsedEmoji>>

    @Query("SELECT * FROM Frequently_Used_Emoji_Database WHERE id = :id")
    fun getEmojisById(id: Int): FrequentlyUsedEmoji?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(frequentlyUsedEmoji: FrequentlyUsedEmoji)

    @Delete
    fun delete(frequentlyUsedEmoji: FrequentlyUsedEmoji)

    @Transaction
    fun deleteAndInsertEmoji(newData: FrequentlyUsedEmoji, oldData: FrequentlyUsedEmoji) {
        delete(oldData)
        insert(newData)
    }
}