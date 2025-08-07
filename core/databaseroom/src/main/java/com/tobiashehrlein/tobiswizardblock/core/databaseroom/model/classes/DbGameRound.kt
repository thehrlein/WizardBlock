package com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes

import androidx.room.Entity

@Entity(tableName = "game_rounds", primaryKeys = ["gameId", "round"])
data class DbGameRound(
    val gameId: Long,
    val round: Int,
    val playerTipData: List<DbPlayerTipData>?,
    val playerResultData: List<DbPlayerResultData>?,
    val trumpType: DbTrumpType
)
