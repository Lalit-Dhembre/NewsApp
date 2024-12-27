package com.cosmicstruck.newsapp.onboarding.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.cosmicstruck.newsapp.onboarding.domain.repository.LocalUserManager
import com.cosmicstruck.newsapp.utils.USER_SETTINGS
import com.cosmicstruck.newsapp.utils.APP_ENTRY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)
private object PreferencesKeys{
    val APP_ENTER = booleanPreferencesKey(name = APP_ENTRY)
}

class DatastoreRepository (
    private val context: Context
) : LocalUserManager{
    override suspend fun saveAppEntry(value : Boolean) {
        context.dataStore.edit {setting->
            setting[PreferencesKeys.APP_ENTER] = value
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map {preference->
            preference[PreferencesKeys.APP_ENTER] ?: true
        }
    }
}