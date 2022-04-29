package com.tobiashehrlein.tobiswizardblock.interactor.datasource.firebase

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.tracking.WizardBlockTrackingEvent
import com.tobiashehrlein.tobiswizardblock.entities.tracking.WizardBlockUserProperty

interface AnalyticsDatasource {

    suspend fun trackEvent(wizardBlockTrackingEvent: WizardBlockTrackingEvent): AppResult<Unit>

    suspend fun setUserProperty(wizardBlockUserProperty: WizardBlockUserProperty) :AppResult<Unit>
}
