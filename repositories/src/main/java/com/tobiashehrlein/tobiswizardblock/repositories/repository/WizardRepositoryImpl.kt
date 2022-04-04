package com.tobiashehrlein.tobiswizardblock.repositories.repository

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.tracking.TrackingEvent
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.firebase.AnalyticsDatasource
import com.tobiashehrlein.tobiswizardblock.interactor.repository.WizardRepository

class WizardRepositoryImpl(
    private val analyticsDatasource: AnalyticsDatasource
) : WizardRepository {

    override suspend fun trackAnalyticsEvent(trackingEvent: TrackingEvent) : AppResult<Unit> {
        return analyticsDatasource.trackEvent(trackingEvent)
    }
}