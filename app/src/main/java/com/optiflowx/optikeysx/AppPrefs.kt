package com.optiflowx.optikeysx

import com.optiflowx.optikeysx.core.enums.KeepScreenAwakeMode
import com.optiflowx.optikeysx.core.enums.KeyboardFontType
import com.optiflowx.optikeysx.core.utils.ModelListSerializer
import dev.patrickgold.jetpref.datastore.JetPref
import dev.patrickgold.jetpref.datastore.model.PreferenceModel

// Defining a getter function for easy retrieval of the AppPrefs model.
// You can name this however you want, the convention is <projectName>PreferenceModel
fun optikeysxPreferences() = JetPref.getOrCreatePreferenceModel(AppPrefs::class, ::AppPrefs)

// Defining a preference model for our app prefs
// The name we give here is the file name of the preferences and is saved
// within the app's `jetpref_datastore` directory.
class AppPrefs : PreferenceModel("optikeysx-app-preferences") {
    val modelsOrder = custom(
        key = "sl_models_order",
        default = listOf(),
        serializer = ModelListSerializer()
    )

    val isEnableMemoji = boolean(
        key = "is_enable_memoji",
        default = false,
    )

    val isEnableAccents = boolean(
        key = "is_enable_accents",
        default = false,
    )

    val isEnableSpeechRecognition = boolean(
        key = "is_enable_speech_recognition",
        default = false,
    )

    val recognitionState = int(
        key = "recognition_state",
        default = 0,
    )

    val keepScreenAwake = enum(
        key = "e_keep_screen_awake",
        default = KeepScreenAwakeMode.NEVER
    )

    val keyboardFontType = enum(
        key = "keyboard_font_type",
        default = KeyboardFontType.Regular
    )

    val keepLanguageModelInMemory = boolean(
        key = "keep_language_model_in_memory",
        default = false
    )

    val isAutoCorrect = boolean(
        key = "is_auto_correct",
        default = false,
    )

    val isCapsLock = boolean(
        key = "is_caps_lock",
        default = false,
    )

    val isAllCaps = boolean(
        key = "is_all_caps",
        default = false,
    )

    val isCheckSpelling = boolean(
        key = "is_check_spelling",
        default = false,
    )

    val isAutoCapitalisation = boolean(
        key = "is_auto_capitalisation",
        default = true,
    )

    val isVibrateOnKeypress = boolean(
        key = "is_vibrate_on_keypress",
        default = false,
    )

    val isSoundOnKeypress = boolean(
        key = "is_sound_on_keypress",
        default = false,
    )

    val isCharacterPreview = boolean(
        key = "is_character_preview",
        default = false,
    )

    val isPredictive = boolean(
        key = "is_predictive",
        default = false,
    )

    val isEnableCapsLock = boolean(
        key = "is_enable_caps_lock",
        default = true,
    )
    
    val isDotShortcut = boolean(
        key = "is_dot_shortcut",
        default = false,
    )
}