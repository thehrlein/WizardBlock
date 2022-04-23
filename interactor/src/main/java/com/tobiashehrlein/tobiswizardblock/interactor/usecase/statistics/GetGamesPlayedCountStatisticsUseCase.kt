package com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.statistics.TopPointsStatisticsData
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class GetGamesPlayedCountStatisticsUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Unit, Int>() {

    override suspend fun execute(parameters: Unit): AppResult<Int> {
        return gameRepository.getGamesPlayedCountStatistics()
    }
}