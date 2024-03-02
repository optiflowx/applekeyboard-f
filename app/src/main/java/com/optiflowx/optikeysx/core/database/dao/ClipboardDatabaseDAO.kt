package com.optiflowx.optikeysx.core.database.dao

import androidx.compose.runtime.Immutable
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.optiflowx.optikeysx.core.database.entities.ClipData

@Dao
@Immutable
interface ClipboardDatabaseDAO {
    @Query("SELECT * FROM Clipboard_Database ORDER BY id ASC")
    fun getAllClipData(): LiveData<List<ClipData>>

    @Query("SELECT * FROM Clipboard_Database WHERE id = :id")
    fun getClipDataByID(id: Int): ClipData?

    @Delete
    fun clear(clipData: List<ClipData>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(clipData: ClipData)

    @Delete
    fun delete(clipData: ClipData)
}