package com.tobiashehrlein.tobiswizardblock.core.entities.savedgames

import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.GameSettings

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
