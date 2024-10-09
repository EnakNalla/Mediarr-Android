package com.github.enaknalla.mediarrtv.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

const val PREFERENCES_KEY = "MEDIARR_PREFERENCES"

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_KEY)

class Preferences(context: Context) {
    private val dataStore = context.dataStore

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String? {
        return dataStore.data.first()[TOKEN_KEY]
    }

    companion object {
        val TOKEN_KEY = stringPreferencesKey("TOKEN")
    }
}
