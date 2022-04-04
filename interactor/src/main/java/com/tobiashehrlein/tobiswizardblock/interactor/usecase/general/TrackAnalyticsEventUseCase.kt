package com.tobiashehrlein.tobiswizardblock.interactor.usecase.general

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.tracking.TrackingEvent
import com.tobiashehrlein.tobiswizardblock.interactor.repository.WizardRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class TrackAnalyticsEventUseCase(
    private val wizardRepository: WizardRepository
) : BaseUseCase<TrackingEvent, Unit>() {

    override suspend fun execute(parameters: TrackingEvent): AppResult<Unit> {
        return wizardRepository.trackAnalyticsEvent(parameters)
    }
}
