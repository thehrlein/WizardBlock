package com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.StatisticsRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class GetGamesPlayedCountStatisticsUseCase(
    private val statisticsRepository: StatisticsRepository
) : BaseUseCase<Unit, Int>() {

    override suspend fun execute(parameters: Unit): AppResult<Int> {
        return statisticsRepository.getGamesPlayedCountStatistics()
    }
}