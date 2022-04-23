package com.tobiashehrlein.tobiswizardblock.presentation.statistics

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseToolbarViewModelImpl

abstract class StatisticsViewModel : BaseToolbarViewModelImpl() {

    abstract val mostWinStatisticsData: LiveData<List<MostWinStatisticsData>>
    abstract val playerCountStatistics: LiveData<Map<Int, Int>>
}