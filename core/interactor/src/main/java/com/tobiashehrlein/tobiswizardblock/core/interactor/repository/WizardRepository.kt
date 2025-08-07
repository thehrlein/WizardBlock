package com.tobiashehrlein.tobiswizardblock.core.interactor.repository

import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.entities.tracking.WizardBlockTrackingEvent
import com.tobiashehrlein.tobiswizardblock.core.entities.tracking.WizardBlockUserProperty

interface WizardRepository {

    suspend fun trackAnalyticsEvent(wizardBlockTrackingEvent: WizardBlockTrackingEvent): AppResult<Unit>

    suspend fun trackAnalyticsUserProperty(wizardBlockUserProperty: WizardBlockUserProperty): AppResult<Unit>
}
