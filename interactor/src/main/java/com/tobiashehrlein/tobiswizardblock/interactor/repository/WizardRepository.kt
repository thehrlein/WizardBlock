package com.tobiashehrlein.tobiswizardblock.interactor.repository

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.tracking.WizardBlockTrackingEvent
import com.tobiashehrlein.tobiswizardblock.entities.tracking.WizardBlockUserProperty

interface WizardRepository {

    suspend fun trackAnalyticsEvent(wizardBlockTrackingEvent: WizardBlockTrackingEvent): AppResult<Unit>

    suspend fun trackAnalyticsUserProperty(wizardBlockUserProperty: WizardBlockUserProperty): AppResult<Unit>
}
