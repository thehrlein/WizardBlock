package com.tobiashehrlein.tobiswizardblock.core.entities.tracking

data class WizardBlockTrackingEvent(
    val eventName: TrackingEvent,
    val params: Map<TrackingParam, Any>? = null
)
