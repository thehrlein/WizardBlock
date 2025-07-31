package com.tobiashehrlein.tobiswizardblock.core.presentation.about

import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.tracking.TrackingEvent
import com.tobiashehrlein.tobiswizardblock.entities.tracking.TrackingParam
import com.tobiashehrlein.tobiswizardblock.entities.tracking.WizardBlockTrackingEvent
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.general.TrackAnalyticsEventUseCase
import com.tobiashehrlein.tobiswizardblock.core.presentation.general.SingleLiveEvent
import kotlinx.coroutines.launch

class AboutViewModelImpl(
    private val trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase
) : AboutViewModel() {

    override val sendEmail = SingleLiveEvent<Unit>()
    override val openWizard = SingleLiveEvent<Unit>()
    override val openFahrstuhl = SingleLiveEvent<Unit>()
    override val openMovieBase = SingleLiveEvent<Unit>()

    override fun onFabClicked() {
        sendEmail.call()
    }

    override fun onWizardBlockClicked() {
        openWizard.call()
    }

    override fun onFahrstuhlBlockClicked() {
        openFahrstuhl.call()
    }

    override fun onMovieBaseClicked() {
        openMovieBase.call()
    }

    override fun trackOpenStoreEvent(packageName: String) {
        viewModelScope.launch {
            trackAnalyticsEventUseCase.invoke(
                WizardBlockTrackingEvent(
                    eventName = TrackingEvent.OPEN_PLAY_STORE,
                    params = mapOf(
                        TrackingParam.APP_NAME to packageName
                    )
                )
            )
        }
    }
}
