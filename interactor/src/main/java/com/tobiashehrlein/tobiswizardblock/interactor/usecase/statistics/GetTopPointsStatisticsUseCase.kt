package com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.statistics.TopPointsStatisticsData
import com.tobiashehrlein.tobiswizardblock.interactor.repository.StatisticsRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class GetTopPointsStatisticsUseCase(
    private val statisticsRepository: StatisticsRepository
) : BaseUseCase<Unit, List<TopPointsStatisticsData>>() {

    override suspend fun execute(parameters: Unit): AppResult<List<TopPointsStatisticsData>> {
        return statisticsRepository.getTopPointsStatistics()
    }
}