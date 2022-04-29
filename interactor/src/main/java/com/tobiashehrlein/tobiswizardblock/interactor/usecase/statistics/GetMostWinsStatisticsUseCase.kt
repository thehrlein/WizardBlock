package com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.interactor.repository.StatisticsRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class GetMostWinsStatisticsUseCase(
    private val statisticsRepository: StatisticsRepository
) : BaseUseCase<Unit, List<MostWinStatisticsData>>() {

    override suspend fun execute(parameters: Unit): AppResult<List<MostWinStatisticsData>> {
        return statisticsRepository.getMostWinsStatistics()
    }
}