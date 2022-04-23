package com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class GetMostWinsStatisticsUseCase(
    private val gameRepository: GameRepository
) : BaseUseCase<Unit, List<MostWinStatisticsData>>() {

    override suspend fun execute(parameters: Unit): AppResult<List<MostWinStatisticsData>> {
        return gameRepository.getMostWinsStatistics()
    }
}