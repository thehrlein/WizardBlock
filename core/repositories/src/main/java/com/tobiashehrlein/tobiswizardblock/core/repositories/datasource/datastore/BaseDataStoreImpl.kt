package com.tobiashehrlein.tobiswizardblock.core.repositories.datasource.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.general.toSuccessResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class BaseDataStoreImpl {

    abstract val dataStore: DataStore<Preferences>

    fun getIntValue(prefKey: String, defaultValue: Int): Flow<AppResult<Int>> {
        return dataStore.data.map { preference ->
            preference[intPreferencesKey(prefKey)] ?: defaultValue
        }.map {
            it.toSuccessResult()
        }
    }

    suspend fun setIntValue(prefKey: String, value: Int): AppResult<Unit> {
        dataStore.edit { settings ->
            settings[intPreferencesKey(prefKey)] = value
        }
        return Unit.toSuccessResult()
    }

    fun getBooleanValue(prefKey: String, defaultValue: Boolean): Flow<AppResult<Boolean>> {
        return dataStore.data.map { preference ->
            preference[booleanPreferencesKey(prefKey)] ?: defaultValue
        }.map {
            it.toSuccessResult()
        }
    }

    suspend fun setBooleanValue(prefKey: String, value: Boolean): AppResult<Unit> {
        dataStore.edit { settings ->
            settings[booleanPreferencesKey(prefKey)] = value
        }
        return Unit.toSuccessResult()
    }
}
