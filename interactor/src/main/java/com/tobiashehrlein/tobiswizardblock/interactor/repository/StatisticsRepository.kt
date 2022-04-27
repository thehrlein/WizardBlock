package com.tobiashehrlein.tobiswizardblock.interactor.repository

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.TopPointsStatisticsData

interface StatisticsRepository {

    suspend fun getMostWinsStatistics(): AppResult<List<MostWinStatisticsData>>

    suspend fun getPlayerCountStatistics(): AppResult<Map<Int, Int>>

    suspend fun getTopPointsStatistics(): AppResult<List<TopPointsStatisticsData>>

    suspend fun getGamesPlayedCountStatistics(): AppResult<Int>

    suspend fun clearStatistics(): AppResult<Unit>
}