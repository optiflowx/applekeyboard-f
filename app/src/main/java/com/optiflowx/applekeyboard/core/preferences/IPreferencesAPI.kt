package com.optiflowx.applekeyboard.core.preferences

import androidx.compose.runtime.Immutable
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

@Immutable
interface IPreferencesAPI {
    fun <T> getFlowPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T>
    suspend fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): T
    suspend fun <T> putPreference(key: Preferences.Key<T>, value: T)
    suspend fun <T> removePreference(key: Preferences.Key<T>)
    suspend fun clearAllPreferences()
}