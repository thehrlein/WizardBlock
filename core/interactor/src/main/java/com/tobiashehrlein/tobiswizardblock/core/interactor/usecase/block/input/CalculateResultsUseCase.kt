package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block.input

import com.tobiashehrlein.tobiswizardblock.entities.game.general.PlayerResultData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.CalculateResultData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class CalculateResultsUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<CalculateResultData, List<PlayerResultData>>() {

    override suspend fun execute(parameters: CalculateResultData): AppResult<List<PlayerResultData>> {
        return gameRepository.calculateResults(parameters)
    }
}
