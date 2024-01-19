package com.optiflowx.applekeyboard.storage

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface IPreferencesAPI {
    fun <T> getFlowPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T>
    suspend fun <T> getStaticPreference(key: Preferences.Key<T>, defaultValue: T): T
    suspend fun <T> putPreference(key: Preferences.Key<T>, value: T)
    suspend fun <T> removePreference(key: Preferences.Key<T>)
    suspend fun clearAllPreferences()
}