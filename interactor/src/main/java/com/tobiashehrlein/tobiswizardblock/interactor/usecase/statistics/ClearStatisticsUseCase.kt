package com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.repository.StatisticsRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class ClearStatisticsUseCase(
    private val statisticsRepository: StatisticsRepository
) : BaseUseCase<Unit, Unit>() {

    override suspend fun execute(parameters: Unit): AppResult<Unit> {
        return statisticsRepository.clearStatistics()
    }
}