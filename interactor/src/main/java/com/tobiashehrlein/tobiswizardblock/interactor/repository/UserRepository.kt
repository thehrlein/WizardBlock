package com.tobiashehrlein.tobiswizardblock.interactor.repository

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun isShowTrumpDialogEnabled(): AppResult<Boolean>
    suspend fun setShowTrumpDialogEnabled(enabled: Boolean): AppResult<Unit>
    fun getDisplayAlwaysOn() : Flow<AppResult<Boolean>>
    suspend fun setDisplayAlwaysOn(alwaysOn: Boolean) : AppResult<Unit>
}
