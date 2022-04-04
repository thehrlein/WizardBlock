package com.tobiashehrlein.tobiswizardblock.interactor.repository

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult

interface UserRepository {

    suspend fun isShowTrumpDialogEnabled(): AppResult<Boolean>

    suspend fun setShowTrumpDialogEnabled(enabled: Boolean): AppResult<Unit>
}
