package com.tobiashehrlein.tobiswizardblock.core.entities.statistics

data class GameRulesStatisticsData(
    val noGamesPlayed: Boolean,
    val tipsEqualStitches: Int,
    val tipsEqualStitchesFirstRound: Int,
    val anniversaryVersion: Int
)
