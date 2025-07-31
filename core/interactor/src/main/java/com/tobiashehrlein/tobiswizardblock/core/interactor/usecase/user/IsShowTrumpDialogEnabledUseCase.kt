package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.user

import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.UserRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class IsShowTrumpDialogEnabledUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<Unit, Boolean>() {

    override suspend fun execute(parameters: Unit): AppResult<Boolean> {
        return userRepository.isShowTrumpDialogEnabled()
    }
}
