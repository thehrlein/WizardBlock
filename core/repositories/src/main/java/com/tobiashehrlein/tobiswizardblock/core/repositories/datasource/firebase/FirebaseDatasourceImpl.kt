package com.tobiashehrlein.tobiswizardblock.core.repositories.datasource.firebase

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.entities.tracking.TrackingParam
import com.tobiashehrlein.tobiswizardblock.core.entities.tracking.WizardBlockTrackingEvent
import com.tobiashehrlein.tobiswizardblock.core.entities.tracking.WizardBlockUserProperty
import com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.BaseDatasource
import com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.firebase.AnalyticsDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebaseDatasourceImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : BaseDatasource, AnalyticsDatasource {

    override suspend fun trackEvent(wizardBlockTrackingEvent: WizardBlockTrackingEvent): AppResult<Unit> {
        return safeCall {
            firebaseAnalytics.logEvent(
                wizardBlockTrackingEvent.eventName.name.lowercase(),
                wizardBlockTrackingEvent.params?.toFirebaseParamBundle()
            )
        }
    }

    override suspend fun setUserProperty(wizardBlockUserProperty: WizardBlockUserProperty): AppResult<Unit> =
        withContext(Dispatchers.IO) {
            safeCall {
                firebaseAnalytics.setUserProperty(
                    wizardBlockUserProperty.userProperty.name.lowercase(),
                    wizardBlockUserProperty.value
                )
            }
        }

    private fun Map<TrackingParam, Any>.toFirebaseParamBundle(): Bundle {
        val bundle = Bundle()
        forEach {
            val paramName = it.key.name.lowercase()
            when (val value = it.value) {
                is String -> bundle.putString(paramName, value)
                is Int -> bundle.putInt(paramName, value)
                is Long -> bundle.putLong(paramName, value)
                is Boolean -> bundle.putString(paramName, value.toString())
                else -> error("firebase param value not defined")
            }
        }
        return bundle
    }
}
