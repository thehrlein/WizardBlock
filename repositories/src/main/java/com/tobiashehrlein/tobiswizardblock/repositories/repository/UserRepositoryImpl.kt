package com.tobiashehrlein.tobiswizardblock.repositories.repository

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.sharedpref.UserSettingsPersistence
import com.tobiashehrlein.tobiswizardblock.interactor.repository.UserRepository

class UserRepositoryImpl(
    private val userSettingsPersistence: UserSettingsPersistence
) : UserRepository {

    override suspend fun isShowTrumpDialogEnabled(): AppResult<Boolean> {
        return AppResult.Success(userSettingsPersistence.isShowTrumpDialogEnabled)
    }

    override suspend fun setShowTrumpDialogEnabled(enabled: Boolean): AppResult<Unit> {
        userSettingsPersistence.isShowTrumpDialogEnabled = enabled
        return AppResult.Success(Unit)
    }
}
