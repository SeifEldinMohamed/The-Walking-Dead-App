package com.seif.thewalkingdeadapp.data.local.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.seif.thewalkingdeadapp.domain.repository.DataStoreOperations
import com.seif.thewalkingdeadapp.utils.Constants.PREFERENCES_KEY
import com.seif.thewalkingdeadapp.utils.Constants.PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME) // this extension variable used to access our datastore

class DataStoreOperationsImp(context: Context) : DataStoreOperations {
    private object PreferenceKey {
        val onBoardingKey = booleanPreferencesKey(name = PREFERENCES_KEY)
    }

    private val datastore = context.datastore

    override suspend fun saveOnBoardingState(completed: Boolean) {
        datastore.edit { preferences ->
            preferences[PreferenceKey.onBoardingKey] = completed
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return datastore.data
            .catch { exception ->
                if (exception is IOException)
                    emit(emptyPreferences())
                 else
                    throw exception
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferenceKey.onBoardingKey] ?: false // if null return default value
                onBoardingState
            }
    }

}