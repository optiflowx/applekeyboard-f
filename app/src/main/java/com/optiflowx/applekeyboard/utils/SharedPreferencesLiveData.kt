package com.optiflowx.applekeyboard.utils

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

abstract class SharedPreferenceLiveData<T>(
    val sharedPrefs: SharedPreferences, val key: String, private val defValue: T
) : LiveData<T>() {
    //Listener
    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == this.key) {
            value = getValueFromPreferences(key, defValue)
        }
    }

    abstract fun getValueFromPreferences(key: String, defValue: T): T?

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharedPrefs.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(listener)
        super.onInactive()
    }
}

class SharedPreferenceIntLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Int)
    : SharedPreferenceLiveData<Int>(sharedPrefs, key, defValue) {

    override fun getValueFromPreferences(key: String, defValue: Int): Int {
        return sharedPrefs.getInt(key, defValue)
    }
}

class SharedPreferenceStringLiveData(sharedPrefs: SharedPreferences, key: String, defValue: String)
    : SharedPreferenceLiveData<String>(sharedPrefs, key, defValue) {

    override fun getValueFromPreferences(key: String, defValue: String): String? {
        return sharedPrefs.getString(key, defValue)
    }
}

class SharedPreferenceBooleanLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Boolean)
    : SharedPreferenceLiveData<Boolean>(sharedPrefs, key, defValue) {

    override fun getValueFromPreferences(key: String, defValue: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, defValue)
    }
}

//class SharedPreferenceFloatLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Float)
//    : SharedPreferenceLiveData<Float>(sharedPrefs, key, defValue) {
//
//    override fun getValueFromPreferences(key: String, defValue: Float): Float {
//        return sharedPrefs.getFloat(key, defValue)
//    }
//}
//
//class SharedPreferenceLongLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Long)
//    : SharedPreferenceLiveData<Long>(sharedPrefs, key, defValue) {
//
//    override fun getValueFromPreferences(key: String, defValue: Long): Long {
//        return sharedPrefs.getLong(key, defValue)
//    }
//}

//class SharedPreferenceStringSetLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Set<String>)
//    : SharedPreferenceLiveData<Set<String>>(sharedPrefs, key, defValue) {
//
//    override fun getValueFromPreferences(key: String, defValue: Set<String>): Set<String>? {
//        return sharedPrefs.getStringSet(key, defValue)
//    }
//}

//fun SharedPreferences.floatLiveData(key: String, defValue: Float): SharedPreferenceLiveData<Float> {
//    return SharedPreferenceFloatLiveData(this, key, defValue)
//}
//
//fun SharedPreferences.longLiveData(key: String, defValue: Long): SharedPreferenceLiveData<Long> {
//    return SharedPreferenceLongLiveData(this, key, defValue)
//}

fun SharedPreferences.stringLiveData(key: String, defValue: String):
        SharedPreferenceLiveData<String> {
    return SharedPreferenceStringLiveData(this, key, defValue)
}

fun SharedPreferences.intLiveData(key: String, defValue: Int): SharedPreferenceLiveData<Int> {
    return SharedPreferenceIntLiveData(this, key, defValue)
}

fun SharedPreferences.booleanLiveData(key: String, defValue: Boolean): SharedPreferenceLiveData<Boolean> {
    return SharedPreferenceBooleanLiveData(this, key, defValue)
}

//fun SharedPreferences.stringSetLiveData(key: String, defValue: Set<String>):
//        SharedPreferenceLiveData<Set<String>> {
//    return SharedPreferenceStringSetLiveData(this, key, defValue)
//}