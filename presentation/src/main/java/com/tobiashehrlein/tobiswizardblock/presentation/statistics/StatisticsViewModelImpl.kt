package com.tobiashehrlein.tobiswizardblock.presentation.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.TopPointsStatisticsData
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.invoke
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetMostWinsStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetPlayerCountStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetTopPointsStatisticsUseCase
import kotlinx.coroutines.launch

class StatisticsViewModelImpl(
    private val getMostWinsStatisticsUseCase: GetMostWinsStatisticsUseCase,
    private val getPlayerCountStatisticsUseCase: GetPlayerCountStatisticsUseCase,
    private val getTopPointsStatisticsUseCase: GetTopPointsStatisticsUseCase
) : StatisticsViewModel() {

    override val mostWinStatisticsData = MutableLiveData<List<MostWinStatisticsData>>()
    override val playerCountStatistics = MutableLiveData<Map<Int, Int>>()
    override val topPointsStatisticsData = MutableLiveData<List<TopPointsStatisticsData>>()

    init {
        getMostWins()
        getPlayerCount()
        getTopPoints()
    }

    private fun getMostWins() {
        viewModelScope.launch {
            when (val result = getMostWinsStatisticsUseCase.invoke()) {
                is AppResult.Success -> mostWinStatisticsData.value = result.value
                is AppResult.Error -> Unit
            }
        }
    }

    private fun getPlayerCount() {
        viewModelScope.launch {
            when (val result = getPlayerCountStatisticsUseCase.invoke()) {
                is AppResult.Success -> playerCountStatistics.value = result.value
                is AppResult.Error -> Unit
            }
        }
    }

    private fun getTopPoints() {
        viewModelScope.launch {
            when (val result = getTopPointsStatisticsUseCase.invoke()) {
                is AppResult.Success -> topPointsStatisticsData.value = result.value
                is AppResult.Error -> Unit
            }
        }
    }
}