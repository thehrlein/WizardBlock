package com.tobiashehrlein.tobiswizardblock.presentation.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.entities.statistics.GameDayStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.TopPointsStatisticsData
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.invoke
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.ClearStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetGameDayStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetGamesPlayedCountStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetMostWinsStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetPlayerCountStatisticsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.statistics.GetTopPointsStatisticsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val NO_GAMES_FINISHED = 0
private const val STATISTICS_LOADING_DELAY: Long = 500

class StatisticsViewModelImpl(
    private val getMostWinsStatisticsUseCase: GetMostWinsStatisticsUseCase,
    private val getPlayerCountStatisticsUseCase: GetPlayerCountStatisticsUseCase,
    private val getTopPointsStatisticsUseCase: GetTopPointsStatisticsUseCase,
    private val getGamesPlayedCountStatisticsUseCase: GetGamesPlayedCountStatisticsUseCase,
    private val getGameDayStatisticsUseCase: GetGameDayStatisticsUseCase,
    private val clearStatisticsUseCase: ClearStatisticsUseCase
) : StatisticsViewModel() {

    override val showLoading = MutableLiveData<Boolean>()
    override val mostWinStatisticsData = MutableLiveData<List<MostWinStatisticsData>>()
    override val playerCountStatistics = MutableLiveData<Map<Int, Int>>()
    override val topPointsStatisticsData = MutableLiveData<List<TopPointsStatisticsData>>()
    override val gamesPlayedCountStatistics = MutableLiveData<Int>()
    override val gameDayStatisticsData = MutableLiveData<GameDayStatisticsData>()
    override val anyStatisticsAvailable = MediatorLiveData<Boolean>().also { mediator ->
        mediator.addSource(mostWinStatisticsData) {
            mediator.value = it.isNotEmpty() ||
                    playerCountStatistics.value.isNullOrEmpty().not() ||
                    topPointsStatisticsData.value.isNullOrEmpty().not() ||
                    (gamesPlayedCountStatistics.value != null && gamesPlayedCountStatistics.value != NO_GAMES_FINISHED) ||
                    gameDayStatisticsData.value != null
        }
        mediator.addSource(playerCountStatistics) {
            mediator.value = it.isNotEmpty() ||
                    mostWinStatisticsData.value.isNullOrEmpty().not() ||
                    topPointsStatisticsData.value.isNullOrEmpty().not() ||
                    (gamesPlayedCountStatistics.value != null && gamesPlayedCountStatistics.value != NO_GAMES_FINISHED) ||
                    gameDayStatisticsData.value != null
        }
        mediator.addSource(topPointsStatisticsData) {
            mediator.value = it.isNotEmpty() ||
                    mostWinStatisticsData.value.isNullOrEmpty().not() ||
                    playerCountStatistics.value.isNullOrEmpty().not() ||
                    (gamesPlayedCountStatistics.value != null && gamesPlayedCountStatistics.value != NO_GAMES_FINISHED) ||
                    gameDayStatisticsData.value != null
        }
        mediator.addSource(gamesPlayedCountStatistics) {
            mediator.value = (it != null && it != NO_GAMES_FINISHED) ||
                    mostWinStatisticsData.value.isNullOrEmpty().not() ||
                    playerCountStatistics.value.isNullOrEmpty().not() ||
                    topPointsStatisticsData.value.isNullOrEmpty().not() ||
                    gameDayStatisticsData.value != null
        }
        mediator.addSource(gameDayStatisticsData) {
            mediator.value = it != null ||
                    mostWinStatisticsData.value.isNullOrEmpty().not() ||
                    playerCountStatistics.value.isNullOrEmpty().not() ||
                    topPointsStatisticsData.value.isNullOrEmpty().not() ||
                    (gamesPlayedCountStatistics.value != null && gamesPlayedCountStatistics.value != NO_GAMES_FINISHED)
        }
    }

    init {
        getStatistics()
    }

    private fun getStatistics() {
        viewModelScope.launch {
            showLoading.value = true
            getMostWins()
            getPlayerCount()
            getTopPoints()
            getGamesPlayed()
            getGameDayStatistics()
            delay(STATISTICS_LOADING_DELAY)
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

    private fun getGameDayStatistics() {
        viewModelScope.launch {
            when (val result = getGameDayStatisticsUseCase.invoke()) {
                is AppResult.Success -> gameDayStatisticsData.value = result.value
                is AppResult.Error -> Unit
            }
        }
    }

    override fun onClearActionClicked() {
        navigateTo(Page.Statistics.Clear)
    }

    override fun onClearStatisticsConfirmed() {
        viewModelScope.launch {
            when (val result = clearStatisticsUseCase.invoke()) {
                is AppResult.Success -> getStatistics()
                is AppResult.Error -> Unit
            }
        }
    }
}