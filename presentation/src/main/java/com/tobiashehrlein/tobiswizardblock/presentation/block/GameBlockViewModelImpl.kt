package com.tobiashehrlein.tobiswizardblock.presentation.block

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.entities.tracking.TrackingEvent
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.general.TrackAnalyticsEventUseCase
import kotlinx.coroutines.launch

class GameBlockViewModelImpl(
    gameId: Long,
    private val trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase
) : GameBlockViewModel() {

    override val gameId = MutableLiveData(gameId)

    init {
        trackGameStarted()
    }

    private fun trackGameStarted() {
        viewModelScope.launch {
            trackAnalyticsEventUseCase.invoke(TrackingEvent.NEW_GAME_STARTED)
        }
    }

    override fun trackGameFinished() {
        viewModelScope.launch {
            trackAnalyticsEventUseCase.invoke(TrackingEvent.GAME_FINISHED)
        }
    }

    override fun openMenu() {
        navigateTo(Page.Block.Menu)
    }
}
