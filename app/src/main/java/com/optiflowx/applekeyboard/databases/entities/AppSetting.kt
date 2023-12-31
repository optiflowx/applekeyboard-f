package com.optiflowx.applekeyboard.databases.entities
//
//import androidx.compose.runtime.Immutable
//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//
//@Immutable
//@Entity(tableName = "App_Settings_Database")
//data class AppSetting(
//    @PrimaryKey(autoGenerate = false)
//    val id: Int = 0,
//
//    @ColumnInfo(name = "locale")
//    val locale: String = "en",
//
//    @ColumnInfo(name = "font_type")
//    val fontType: String = "regular",
//
//    @ColumnInfo(name = "font_size")
//    val fontSize: Int = 18,
//
//    @ColumnInfo(name = "vibrate_on_key_press")
//    val vibrateOnKeyPress: Boolean = false,
//
//    @ColumnInfo(name = "sound_on_key_press")
//    val soundOnKeyPress: Boolean = true,
//
//    @ColumnInfo(name = "auto_capitalize")
//    val autoCapitalize: Boolean = true,
//
//    @ColumnInfo(name = "auto_correct")
//    val autoCorrect: Boolean = false,
//
//    @ColumnInfo(name = "double_space_period")
//    val doubleSpacePeriod: Boolean = true,
//
//    @ColumnInfo(name = "auto_capitalize_first_word")
//    val autoCapitalizeFirstWord: Boolean = false,
//
//    @ColumnInfo(name = "auto_capitalize_I")
//    val autoCapitalizeI: Boolean = true,
//
//    @ColumnInfo(name = "show_suggestions")
//    val showSuggestions: Boolean = true,
//
//    @ColumnInfo(name = "show_emoji_search_bar")
//    val showEmojiSearchBar: Boolean = true,
//
//    @ColumnInfo(name = "auto_check_spelling")
//    val autoCheckSpelling: Boolean = false,
//
//    )