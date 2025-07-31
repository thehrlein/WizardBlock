package com.tobiashehrlein.tobiswizardblock.core.repositories.repository

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.tracking.WizardBlockTrackingEvent
import com.tobiashehrlein.tobiswizardblock.entities.tracking.WizardBlockUserProperty
import com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.firebase.AnalyticsDatasource
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.WizardRepository

class WizardRepositoryImpl(
    private val analyticsDatasource: AnalyticsDatasource
) : WizardRepository {

    override suspend fun trackAnalyticsEvent(wizardBlockTrackingEvent: WizardBlockTrackingEvent): AppResult<Unit> {
        return analyticsDatasource.trackEvent(wizardBlockTrackingEvent)
    }

    override suspend fun trackAnalyticsUserProperty(wizardBlockUserProperty: WizardBlockUserProperty): AppResult<Unit> {
        return analyticsDatasource.setUserProperty(wizardBlockUserProperty)
    }
}
