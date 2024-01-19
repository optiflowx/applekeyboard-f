package com.optiflowx.applekeyboard.storage
//
//import android.content.Context
//import androidx.datastore.core.Serializer
//import androidx.datastore.dataStore
//import kotlinx.serialization.SerializationException
//import kotlinx.serialization.json.Json
//import java.io.InputStream
//import java.io.OutputStream
//
////Globally Shared Context DataStore
////val Context.dataStore by dataStore(
////    fileName = "keyboard_prefs.pb",
////    serializer = KeyboardPreferencesSerializer
////)
//
//object KeyboardPreferencesSerializer : Serializer<KeyboardPreferences> {
//
//    override val defaultValue: KeyboardPreferences
//        get() = KeyboardPreferences()
//
//    override suspend fun readFrom(input: InputStream): KeyboardPreferences {
//        return try {
//            Json.decodeFromString(
//                deserializer = KeyboardPreferences.serializer(),
//                string = input.readBytes().decodeToString()
//            )
//        } catch (e: SerializationException) {
//            e.printStackTrace()
//            defaultValue
//        }
//    }
//
//    override suspend fun writeTo(t: KeyboardPreferences, output: OutputStream) {
//        output.write(
//            Json.encodeToString(
//                serializer = KeyboardPreferences.serializer(),
//                value = t
//            ).encodeToByteArray()
//        )
//    }
//}