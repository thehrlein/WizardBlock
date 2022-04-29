package com.tobiashehrlein.tobiswizardblock.entities.game.general

import java.io.Serializable

data class PlayerTipData(
    val playerName: String,
    val tip: Int,
    val correctedCauseOfCloudCard: Boolean = false
) : Serializable
