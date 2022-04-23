package com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class GetPlayerCountStatisticsUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Unit, Map<Int, Int>>() {

    override suspend fun execute(parameters: Unit): AppResult<Map<Int, Int>> {
        return gameRepository.getPlayerCountStatistics()
    }
}