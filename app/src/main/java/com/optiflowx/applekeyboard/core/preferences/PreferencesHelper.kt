package com.optiflowx.applekeyboard.core.preferences

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException


private val Context.dataStore by preferencesDataStore(
    name = "AppleKeyboardPreferences_pd"
)

@Immutable
class PreferencesHelper(context: Context) : IPreferencesAPI {
    // dataSource access the DataStore file and does the manipulation based on our requirements.
    @Stable
    private val dataStore = context.dataStore

    /* This returns us a flow of data from DataStore. Basically as soon we update the value in Datastore,
        the values returned by it also changes. */
    @Stable
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
    @Stable
    override suspend fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): T {
        return dataStore.data.first()[key] ?: defaultValue
    }

    @Stable
    override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    @Stable
    override suspend fun <T> removePreference(key: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    @Stable
    override suspend fun clearAllPreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}