package com.optiflowx.applekeyboard.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.optiflowx.applekeyboard.database.entities.EmojiData

@Dao
interface RecentEmojiDatabaseDAO {
    @Query("SELECT * FROM Frequently_Used_Emojis")
    fun getAllEmojis(): LiveData<List<EmojiData>>

    @Query("SELECT * from Frequently_Used_Emojis where id = :id")
    fun getEmojisById(id: Int): EmojiData?

    @Insert
    suspend fun insert(emojiData: EmojiData)

    @Delete
    suspend fun delete(emojiData: EmojiData)
}