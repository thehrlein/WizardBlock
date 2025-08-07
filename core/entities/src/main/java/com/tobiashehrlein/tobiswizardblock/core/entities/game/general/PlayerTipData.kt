package com.tobiashehrlein.tobiswizardblock.core.entities.game.general

import java.io.Serializable

data class PlayerTipData(
    val playerName: String,
    val tip: Int,
    val correctedCauseOfCloudCard: Boolean = false
) : Serializable
