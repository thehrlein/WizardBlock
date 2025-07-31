package com.tobiashehrlein.tobiswizardblock.core.entities.game.general

import java.io.Serializable

data class GameSettings(
    val tipsEqualStitches: Boolean,
    val tipsEqualStitchesFirstRound: Boolean,
    val anniversaryVersion: Boolean
) : Serializable {

    companion object {
        val Default: GameSettings
            get() = GameSettings(
                tipsEqualStitches = false,
                tipsEqualStitchesFirstRound = false,
                anniversaryVersion = false
            )
    }
}
