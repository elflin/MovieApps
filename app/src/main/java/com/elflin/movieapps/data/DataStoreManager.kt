package com.elflin.movieapps.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATA_STORE_NAME = "MovieApps"
class DataStoreManager(context: Context) {

    private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
    private val dataStore = context.dataStore

    companion object {
        val TOKEN_KEY = stringPreferencesKey("token_key")
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    val getToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

}