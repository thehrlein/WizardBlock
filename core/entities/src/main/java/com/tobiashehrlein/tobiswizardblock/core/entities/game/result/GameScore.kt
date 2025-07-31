package com.tobiashehrlein.tobiswizardblock.entities.game.result

import java.io.Serializable

data class GameScore(
    val position: Int,
    val player: String,
    val points: Int
) : Serializable
