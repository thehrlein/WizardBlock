package com.tobiashehrlein.tobiswizardblock.core.repositories.datasource.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.BaseDatasource
import com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.datastore.SettingsDataStore
import kotlinx.coroutines.flow.Flow

val Context.wizardDataStore: DataStore<Preferences> by preferencesDataStore(name = "wizard_data_store")

class WizardDataStoreImpl(
    context: Context
) : BaseDataStoreImpl(), BaseDatasource,
    SettingsDataStore {

    override val dataStore: DataStore<Preferences> = context.wizardDataStore

    override fun getDisplayAlwaysOn(): Flow<AppResult<Boolean>> {
        return getBooleanValue(SettingsDataStore.KEY_DISPLAY_ALWAYS_ON, SettingsDataStore.DEFAULT_DISPLAY_ALWAYS_ON)
    }

    override suspend fun setDisplayAlwaysOn(alwaysOn: Boolean): AppResult<Unit> {
        return setBooleanValue(SettingsDataStore.KEY_DISPLAY_ALWAYS_ON, alwaysOn)
    }
}
