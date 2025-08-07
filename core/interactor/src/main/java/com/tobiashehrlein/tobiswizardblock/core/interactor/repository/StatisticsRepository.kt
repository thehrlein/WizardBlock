package com.tobiashehrlein.tobiswizardblock.core.interactor.repository

import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.entities.statistics.GameDayStatisticsData
import com.tobiashehrlein.tobiswizardblock.core.entities.statistics.GameRulesStatisticsData
import com.tobiashehrlein.tobiswizardblock.core.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.core.entities.statistics.TopPointsStatisticsData

interface StatisticsRepository {

    suspend fun getMostWinsStatistics(): AppResult<List<MostWinStatisticsData>>

    suspend fun getPlayerCountStatistics(): AppResult<Map<Int, Int>>

    suspend fun getTopPointsStatistics(): AppResult<List<TopPointsStatisticsData>>

    suspend fun getGamesPlayedCountStatistics(): AppResult<Int>

    suspend fun getGameDayStatistics(): AppResult<GameDayStatisticsData>

    suspend fun getGameRulesStatistics(): AppResult<GameRulesStatisticsData>

    suspend fun clearStatistics(): AppResult<Unit>
}
