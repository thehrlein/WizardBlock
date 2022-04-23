package com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.statistics.TopPointsStatisticsData
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class GetTopPointsStatisticsUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Unit, List<TopPointsStatisticsData>>() {

    override suspend fun execute(parameters: Unit): AppResult<List<TopPointsStatisticsData>> {
        return gameRepository.getTopPointsStatistics()
    }
}