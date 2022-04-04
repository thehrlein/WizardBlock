package com.tobiashehrlein.tobiswizardblock.interactor.datasource.firebase

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.tracking.TrackingEvent

interface AnalyticsDatasource {

    suspend fun trackEvent(trackingEvent: TrackingEvent): AppResult<Unit>
}
