package com.optiflowx.applekeyboard.storage

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore by preferencesDataStore(
    name = "AppleKeyboardPreferences"
)

class PreferencesHelper(context: Context) : IPreferencesAPI {
    // dataSource access the DataStore file and does the manipulation based on our requirements.
    private val dataStore = context.dataStore


    /* This returns us a flow of data from DataStore. Basically as soon we update the value in Datastore,
        the values returned by it also changes. */
    override fun <T> getFlowPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }.map { preferences ->
            val result = preferences[key] ?: defaultValue
            result
        }
    }

    /* This returns the last saved value of the key. If we change the value,
        it wont effect the values produced by this function */
    override suspend fun <T> getStaticPreference(key: Preferences.Key<T>, defaultValue: T): T {
        return dataStore.data.first()[key] ?: defaultValue
    }

    suspend fun <T> getLastPreference(key: Preferences.Key<T>, defaultValue: T): T {
        return dataStore.data.last()[key]?: defaultValue
    }

    override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun <T> removePreference(key: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    override suspend fun clearAllPreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}