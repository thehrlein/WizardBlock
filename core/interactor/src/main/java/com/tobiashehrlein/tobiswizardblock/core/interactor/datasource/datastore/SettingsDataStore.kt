package com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.datastore

import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import kotlinx.coroutines.flow.Flow

interface SettingsDataStore {

    companion object {
        const val DEFAULT_DISPLAY_ALWAYS_ON = false
        const val KEY_DISPLAY_ALWAYS_ON = "key.display_always_on"
    }

    fun getDisplayAlwaysOn(): Flow<AppResult<Boolean>>
    suspend fun setDisplayAlwaysOn(alwaysOn: Boolean): AppResult<Unit>
}
