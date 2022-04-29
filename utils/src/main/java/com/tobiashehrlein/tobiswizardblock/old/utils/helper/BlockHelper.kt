package com.tobiashehrlein.tobiswizardblock.old.utils.helper

object BlockHelper {

    fun isDealer(
        round: Int,
        maxRound: Int,
        playerCount: Int,
        playerPosition: Int
    ): Boolean {
        val remainder = round % playerCount
        return when {
            round > maxRound -> false
            playerPosition == playerCount -> remainder == 0
            else -> remainder == playerPosition
        }
    }
}
