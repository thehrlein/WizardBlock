package com.tobiashehrlein.tobiswizardblock.entities.savedgames

import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings

data class SavedGameEntity(
    val gameId: Long,
    val gameStartDate: Long,
    val gameName: String,
    val players: List<String>,
    val gameSettings: GameSettings,
    val currentRound: Int,
    val maxRound: Int,
    val gameFinished: Boolean
)
