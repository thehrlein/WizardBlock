package com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.input

import com.tobiashehrlein.tobiswizardblock.entities.game.input.CheckInputValidityData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

data class InputsValidUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<CheckInputValidityData, Boolean>() {

    override suspend fun execute(parameters: CheckInputValidityData): AppResult<Boolean> {
        return gameRepository.checkInputsValidity(parameters)
    }
}
