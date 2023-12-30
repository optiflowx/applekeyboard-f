package com.optiflowx.applekeyboard.utils

import androidx.compose.runtime.Immutable
import androidx.room.TypeConverter
import com.google.gson.Gson

@Immutable
class Converters {
    @TypeConverter
    fun listToJsonString(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
//    @TypeConverter
//    fun setToJsonEnglishWord(value: Set<EnglishWord>?): EnglishWord = Gson().toJson(value)
//
//    @TypeConverter
//    fun jsonEnglishWordToSet(value: EnglishWord) = Gson().fromJson(value, Array<EnglishWord>::class.java).toList().toSet()
//    @TypeConverter
//    fun setToJsonString(value: Set<String>?): String = Gson().toJson(value)
//
//    @TypeConverter
//    fun jsonStringToSet(value: String) = Gson().fromJson(value, Array<String>::class.java).toList().toSet()
//
}