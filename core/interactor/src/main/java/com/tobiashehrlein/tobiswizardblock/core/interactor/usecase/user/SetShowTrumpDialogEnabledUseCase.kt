package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.user

import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.UserRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class SetShowTrumpDialogEnabledUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<Boolean, Unit>() {

    override suspend fun execute(parameters: Boolean): AppResult<Unit> {
        return userRepository.setShowTrumpDialogEnabled(parameters)
    }
}
