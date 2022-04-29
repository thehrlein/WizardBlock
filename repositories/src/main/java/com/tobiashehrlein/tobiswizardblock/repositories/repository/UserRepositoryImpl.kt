package com.tobiashehrlein.tobiswizardblock.repositories.repository

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.datastore.SettingsDataStore
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.sharedpref.UserSettingsPersistence
import com.tobiashehrlein.tobiswizardblock.interactor.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userSettingsPersistence: UserSettingsPersistence,
    private val settingsDataStore: SettingsDataStore
) : UserRepository {

    override suspend fun isShowTrumpDialogEnabled(): AppResult<Boolean> {
        return AppResult.Success(userSettingsPersistence.isShowTrumpDialogEnabled)
    }

    override suspend fun setShowTrumpDialogEnabled(enabled: Boolean): AppResult<Unit> {
        userSettingsPersistence.isShowTrumpDialogEnabled = enabled
        return AppResult.Success(Unit)
    }

    override fun getDisplayAlwaysOn(): Flow<AppResult<Boolean>> {
        return settingsDataStore.getDisplayAlwaysOn()
    }

    override suspend fun setDisplayAlwaysOn(alwaysOn: Boolean): AppResult<Unit> {
        return settingsDataStore.setDisplayAlwaysOn(alwaysOn)
    }
}
