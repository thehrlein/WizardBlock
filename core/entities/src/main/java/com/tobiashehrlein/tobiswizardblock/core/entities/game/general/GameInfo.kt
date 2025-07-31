package com.tobiashehrlein.tobiswizardblock.entities.game.general

import java.io.Serializable

data class GameInfo(
    val gameId: Long = 0,
    val gameStartDate: Long = System.currentTimeMillis(),
    val players: List<String>,
    val gameName: String,
    val gameSettings: GameSettings,
    val gameFinished: Boolean = false
) : Serializable
