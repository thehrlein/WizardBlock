package com.tobiashehrlein.tobiswizardblock.repositories.datasource.firebase

import com.google.firebase.analytics.FirebaseAnalytics
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.tracking.TrackingEvent
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.BaseDatasource
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.firebase.AnalyticsDatasource

class FirebaseDatasourceImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : BaseDatasource, AnalyticsDatasource {

    override suspend fun trackEvent(trackingEvent: TrackingEvent): AppResult<Unit> {
        return safeCall {
            firebaseAnalytics.logEvent(trackingEvent.name, null)
        }
    }
}