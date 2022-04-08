package com.tobiashehrlein.tobiswizardblock.interactor.usecase.settings

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.UserRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseFlowUseCase
import kotlinx.coroutines.flow.Flow

class GetDisplayAlwaysOnUseCase(
    private val userRepository: UserRepository
) : BaseFlowUseCase<Unit, Boolean>() {

    override fun execute(parameters: Unit): Flow<AppResult<Boolean>> {
        return userRepository.getDisplayAlwaysOn()
    }
}