package com.tobiashehrlein.tobiswizardblock.core.entities.game.general

import java.io.Serializable

data class PlayerTipData(
    val playerName: String,
    val tip: Int,
    val correctedCauseOfCloudCard: Boolean = false,
    val cloudCardCorrectionCount: Int = 0,
    val cloudCardCorrectionSteps: List<Int> = emptyList()
) : Serializable {

    val effectiveCloudCardCorrectionCount: Int
        get() = maxOf(
            cloudCardCorrectionCount,
            cloudCardCorrectionSteps.size,
            if (correctedCauseOfCloudCard) 1 else 0
        )
}
