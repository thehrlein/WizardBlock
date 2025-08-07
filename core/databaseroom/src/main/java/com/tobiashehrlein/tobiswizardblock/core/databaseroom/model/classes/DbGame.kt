package com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes

import androidx.room.Embedded
import androidx.room.Relation
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.entities.DbGameInfo

data class DbGame(
    @Embedded
    val gameInfo: DbGameInfo,
    @Relation(
        parentColumn = "gameId",
        entityColumn = "gameId"
    )
    val rounds: List<DbGameRound>
)
