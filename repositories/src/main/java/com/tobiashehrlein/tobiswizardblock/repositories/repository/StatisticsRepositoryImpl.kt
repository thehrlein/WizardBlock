package com.tobiashehrlein.tobiswizardblock.repositories.repository

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.statistics.GameDayStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.TopPointsStatisticsData
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.cache.GameCache
import com.tobiashehrlein.tobiswizardblock.interactor.repository.StatisticsRepository

class StatisticsRepositoryImpl(
    private val gameCache: GameCache
) : StatisticsRepository {

    override suspend fun getMostWinsStatistics(): AppResult<List<MostWinStatisticsData>> {
        return gameCache.getMostWinsStatistics()
    }

    override suspend fun getPlayerCountStatistics(): AppResult<Map<Int, Int>> {
        return gameCache.getPlayerCountStatistics()
    }

    override suspend fun getTopPointsStatistics(): AppResult<List<TopPointsStatisticsData>> {
        return gameCache.getTopPointsStatistics()
    }

    override suspend fun getGamesPlayedCountStatistics(): AppResult<Int> {
        return gameCache.getGamesPlayedCountStatistics()
    }

    override suspend fun getGameDayStatistics(): AppResult<GameDayStatisticsData> {
        return gameCache.getGameDayStatistics()
    }

    override suspend fun clearStatistics(): AppResult<Unit> {
        return gameCache.clearStatistics()
    }
}