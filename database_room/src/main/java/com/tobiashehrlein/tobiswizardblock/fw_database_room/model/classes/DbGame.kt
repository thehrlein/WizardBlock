package com.tobiashehrlein.tobiswizardblock.fw_database_room.model.classes

import androidx.room.Embedded
import androidx.room.Relation
import com.tobiashehrlein.tobiswizardblock.fw_database_room.model.entities.DbGameInfo

data class DbGame(
    @Embedded
    val gameInfo: DbGameInfo,
    @Relation(
        parentColumn = "gameId",
        entityColumn = "gameId"
    )
    val rounds: List<DbGameRound>
)
