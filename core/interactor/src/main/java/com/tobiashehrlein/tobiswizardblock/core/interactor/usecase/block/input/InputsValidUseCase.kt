package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block.input

import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.CheckInputValidityData
import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

data class InputsValidUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<CheckInputValidityData, Boolean>() {

    override suspend fun execute(parameters: CheckInputValidityData): AppResult<Boolean> {
        return gameRepository.checkInputsValidity(parameters)
    }
}
