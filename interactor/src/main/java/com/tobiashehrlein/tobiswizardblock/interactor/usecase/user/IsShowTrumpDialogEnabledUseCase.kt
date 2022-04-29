package com.tobiashehrlein.tobiswizardblock.interactor.usecase.user

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.UserRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class IsShowTrumpDialogEnabledUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<Unit, Boolean>() {

    override suspend fun execute(parameters: Unit): AppResult<Boolean> {
        return userRepository.isShowTrumpDialogEnabled()
    }
}
