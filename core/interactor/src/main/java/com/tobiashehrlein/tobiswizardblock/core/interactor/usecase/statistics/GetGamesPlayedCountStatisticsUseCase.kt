package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.statistics

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.StatisticsRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class GetGamesPlayedCountStatisticsUseCase(
    private val statisticsRepository: StatisticsRepository
) : BaseUseCase<Unit, Int>() {

    override suspend fun execute(parameters: Unit): AppResult<Int> {
        return statisticsRepository.getGamesPlayedCountStatistics()
    }
}
