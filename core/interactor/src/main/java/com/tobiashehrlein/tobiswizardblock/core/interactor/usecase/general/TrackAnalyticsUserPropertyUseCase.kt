package com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.general

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.tracking.WizardBlockUserProperty
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.WizardRepository
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.BaseUseCase

class TrackAnalyticsUserPropertyUseCase(
    private val wizardRepository: WizardRepository
) : BaseUseCase<WizardBlockUserProperty, Unit>() {

    override suspend fun execute(parameters: WizardBlockUserProperty): AppResult<Unit> {
        return wizardRepository.trackAnalyticsUserProperty(parameters)
    }
}
