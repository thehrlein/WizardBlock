package com.tobiashehrlein.tobiswizardblock.presentation.block

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.entities.tracking.TrackingEvent
import com.tobiashehrlein.tobiswizardblock.entities.tracking.TrackingParam
import com.tobiashehrlein.tobiswizardblock.entities.tracking.WizardBlockTrackingEvent
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.general.TrackAnalyticsEventUseCase
import kotlinx.coroutines.launch

class GameBlockViewModelImpl(
    gameId: Long,
    private val trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase
) : GameBlockViewModel() {

    override val gameId = MutableLiveData(gameId)

    override fun trackGameFinished(points: Int) {
        viewModelScope.launch {
            trackAnalyticsEventUseCase.invoke(
                WizardBlockTrackingEvent(
                    eventName = TrackingEvent.GAME_FINISHED,
                    params = mapOf(
                        TrackingParam.POINTS to points.toString()
                    )
                )
            )
        }
    }

    override fun openMenu() {
        navigateTo(Page.Block.Menu)
    }
}
