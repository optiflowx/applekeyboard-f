package com.optiflowx.applekeyboard

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.optiflowx.applekeyboard.database.EmojisDatabase
import com.optiflowx.applekeyboard.database.dao.RecentEmojiDatabaseDAO
import com.optiflowx.applekeyboard.database.entities.EmojiData
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var appDataDao: RecentEmojiDatabaseDAO
    private lateinit var dataB: EmojisDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        dataB = Room.inMemoryDatabaseBuilder(context, EmojisDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        appDataDao = dataB.appDataDAO()
    }

    @After
    @Throws(IOException::class)
    fun deleteDb() {
        if(dataB.isOpen) {
            dataB.close()
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetEmoji() = runBlocking {
        val emojis = listOf("👍", "👎", "👌", "💅")
        for (emoji in  emojis) {
            appDataDao.insert(EmojiData(emoji = emoji))
        }
//        val oneEmoji = appDataDao.getEmojiById(1)
        val allEmojis = appDataDao.getAllEmojis()

        for (emoji in allEmojis) {
            println("Emoji: ${emoji.emoji}")
        }
//        assertEquals(allEmojis[0].id, 1)
//        assertEquals(allEmojis[0].emoji, "👍")
    }
}