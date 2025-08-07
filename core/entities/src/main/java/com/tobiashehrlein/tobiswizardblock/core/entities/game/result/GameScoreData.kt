package com.tobiashehrlein.tobiswizardblock.core.entities.game.result

import java.io.Serializable

data class GameScoreData(
    val gameFinished: Boolean,
    val winnerAlreadyShown: Boolean,
    val results: List<GameScore>
) : Serializable
