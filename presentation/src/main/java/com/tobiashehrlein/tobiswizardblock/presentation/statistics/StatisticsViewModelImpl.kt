package com.tobiashehrlein.tobiswizardblock.presentation.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.TopPointsStatisticsData
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.invoke
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetGamesPlayedCountStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetMostWinsStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetPlayerCountStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetTopPointsStatisticsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DEFAULT_SHOW_LOADING = true

class StatisticsViewModelImpl(
    private val getMostWinsStatisticsUseCase: GetMostWinsStatisticsUseCase,
    private val getPlayerCountStatisticsUseCase: GetPlayerCountStatisticsUseCase,
    private val getTopPointsStatisticsUseCase: GetTopPointsStatisticsUseCase,
    private val getGamesPlayedCountStatisticsUseCase: GetGamesPlayedCountStatisticsUseCase
) : StatisticsViewModel() {

    override val showLoading = MutableLiveData(DEFAULT_SHOW_LOADING)
    override val mostWinStatisticsData = MutableLiveData<List<MostWinStatisticsData>>()
    override val playerCountStatistics = MutableLiveData<Map<Int, Int>>()
    override val topPointsStatisticsData = MutableLiveData<List<TopPointsStatisticsData>>()
    override val gamesPlayedCountStatistics = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            getMostWins()
            getPlayerCount()
            getTopPoints()
            getGamesPlayed()
            showLoading.value = false
        }
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

    private fun getGamesPlayed() {
        viewModelScope.launch {
            when (val result = getGamesPlayedCountStatisticsUseCase.invoke()) {
                is AppResult.Success -> gamesPlayedCountStatistics.value = result.value
                is AppResult.Error -> Unit
            }
        }
    }
}