package com.tobiashehrlein.tobiswizardblock.interactor.repository

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.tracking.TrackingEvent

interface WizardRepository {

    suspend fun trackAnalyticsEvent(trackingEvent: TrackingEvent) : AppResult<Unit>
}