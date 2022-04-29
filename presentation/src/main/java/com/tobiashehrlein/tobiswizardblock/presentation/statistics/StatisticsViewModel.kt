package com.tobiashehrlein.tobiswizardblock.presentation.statistics

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.GameDayStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.GameRulesStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.TopPointsStatisticsData
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseToolbarViewModelImpl

abstract class StatisticsViewModel : BaseToolbarViewModelImpl() {

    abstract val showLoading: LiveData<Boolean>
    abstract val anyStatisticsAvailable: LiveData<Boolean>
    abstract val mostWinStatisticsData: LiveData<List<MostWinStatisticsData>>
    abstract val playerCountStatistics: LiveData<Map<Int, Int>>
    abstract val topPointsStatisticsData: LiveData<List<TopPointsStatisticsData>>
    abstract val gameDayStatisticsData: LiveData<GameDayStatisticsData>
    abstract val gameRulesStatisticsData: LiveData<GameRulesStatisticsData>
    abstract val gamesPlayedCountStatistics: LiveData<Int>

    abstract fun onClearActionClicked()
    abstract fun onClearStatisticsConfirmed()
}