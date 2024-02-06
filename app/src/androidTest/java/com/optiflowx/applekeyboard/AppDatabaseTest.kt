package com.optiflowx.optikeysx

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.optiflowx.optikeysx.core.database.dao.FrequentlyUsedEmojiDatabaseDAO
import com.optiflowx.optikeysx.core.database.dbs.FrequentlyUsedDatabase
import com.optiflowx.optikeysx.core.database.entities.FrequentlyUsedEmoji
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

    private lateinit var appDataDao: FrequentlyUsedEmojiDatabaseDAO
    private lateinit var dataB: FrequentlyUsedDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        dataB = Room.inMemoryDatabaseBuilder(context, FrequentlyUsedDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        appDataDao = dataB.fUsedEmojiDatabaseDAO()
    }

    @After
    @Throws(IOException::class)
    fun deleteDb() {
        if (dataB.isOpen) {
            dataB.close()
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetEmoji() = runBlocking {
        val emojis = listOf("👍", "👎", "👌", "💅")
        for (emoji in emojis) {
            appDataDao.insert(FrequentlyUsedEmoji(id = emoji.hashCode(), emoji = emoji))
        }
//        val oneEmoji = appDataDao.getEmojiById(1)
        val allEmojis = appDataDao.getAllEmojis()

//        for (emoji in allEmojis) {
//            println("Emoji: ${emoji.emoji}")
//        }
//        assertEquals(allEmojis[0].id, 1)
//        assertEquals(allEmojis[0].emoji, "👍")
    }
}