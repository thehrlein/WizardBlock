package com.tobiashehrlein.tobiswizardblock.interactor.usecase.settings

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.UserRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class SetDisplayAlwaysOnUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<Boolean, Unit>() {

    override suspend fun execute(parameters: Boolean): AppResult<Unit> {
        return userRepository.setDisplayAlwaysOn(parameters)
    }
}