package com.tobiashehrlein.tobiswizardblock.entities.statistics

data class GameRulesStatisticsData(
    val noGamesPlayed: Boolean,
    val tipsEqualStitches: Int,
    val tipsEqualStitchesFirstRound: Int,
    val anniversaryVersion: Int
)
