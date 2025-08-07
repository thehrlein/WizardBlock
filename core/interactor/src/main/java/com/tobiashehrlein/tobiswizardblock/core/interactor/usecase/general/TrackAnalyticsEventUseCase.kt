package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.general

import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.entities.tracking.WizardBlockTrackingEvent
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.WizardRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class TrackAnalyticsEventUseCase(
    private val wizardRepository: WizardRepository
) : BaseUseCase<WizardBlockTrackingEvent, Unit>() {

    override suspend fun execute(parameters: WizardBlockTrackingEvent): AppResult<Unit> {
        return wizardRepository.trackAnalyticsEvent(parameters)
    }
}
