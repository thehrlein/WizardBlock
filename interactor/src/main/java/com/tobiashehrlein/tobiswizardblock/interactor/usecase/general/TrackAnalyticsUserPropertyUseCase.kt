package com.tobiashehrlein.tobiswizardblock.interactor.usecase.general

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.tracking.WizardBlockUserProperty
import com.tobiashehrlein.tobiswizardblock.interactor.repository.WizardRepository
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.BaseUseCase

class TrackAnalyticsUserPropertyUseCase(
    private val wizardRepository: WizardRepository
) : BaseUseCase<WizardBlockUserProperty, Unit>() {

    override suspend fun execute(parameters: WizardBlockUserProperty): AppResult<Unit> {
        return wizardRepository.trackAnalyticsUserProperty(parameters)
    }
}
