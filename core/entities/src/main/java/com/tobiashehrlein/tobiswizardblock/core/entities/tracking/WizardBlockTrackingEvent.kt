package com.tobiashehrlein.tobiswizardblock.entities.tracking

data class WizardBlockTrackingEvent(
    val eventName: TrackingEvent,
    val params: Map<TrackingParam, Any>? = null
)
