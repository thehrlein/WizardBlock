package com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes.DbGameSettings

@Entity(tableName = "game_database")
data class DbGameInfo(
    @PrimaryKey(autoGenerate = true)
    val gameId: Long,
    val gameStartDate: Long,
    val players: List<String>,
    val gameName: String,
    @Embedded
    val gameSettings: DbGameSettings,
    val gameFinished: Boolean,
    val removedFromSavedGames: Boolean
)
