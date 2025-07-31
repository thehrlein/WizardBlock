package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.statistics

import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.entities.statistics.GameRulesStatisticsData
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.StatisticsRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class GetGameRulesStatisticsUseCase(
    private val statisticsRepository: StatisticsRepository
) : BaseUseCase<Unit, GameRulesStatisticsData>() {

    override suspend fun execute(parameters: Unit): AppResult<GameRulesStatisticsData> {
        return statisticsRepository.getGameRulesStatistics()
    }
}
