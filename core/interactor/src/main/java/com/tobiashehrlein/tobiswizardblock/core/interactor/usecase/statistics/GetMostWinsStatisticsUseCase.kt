package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.statistics

import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.StatisticsRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class GetMostWinsStatisticsUseCase(
    private val statisticsRepository: StatisticsRepository
) : BaseUseCase<Unit, List<MostWinStatisticsData>>() {

    override suspend fun execute(parameters: Unit): AppResult<List<MostWinStatisticsData>> {
        return statisticsRepository.getMostWinsStatistics()
    }
}
