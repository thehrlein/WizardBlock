package com.tobiashehrlein.tobiswizardblock.core.entities.game.result

import java.io.Serializable

data class GameScore(
    val position: Int,
    val player: String,
    val points: Int
) : Serializable
