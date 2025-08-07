package com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.firebase

import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.entities.tracking.WizardBlockTrackingEvent
import com.tobiashehrlein.tobiswizardblock.core.entities.tracking.WizardBlockUserProperty

interface AnalyticsDatasource {

    suspend fun trackEvent(wizardBlockTrackingEvent: WizardBlockTrackingEvent): AppResult<Unit>

    suspend fun setUserProperty(wizardBlockUserProperty: WizardBlockUserProperty): AppResult<Unit>
}
