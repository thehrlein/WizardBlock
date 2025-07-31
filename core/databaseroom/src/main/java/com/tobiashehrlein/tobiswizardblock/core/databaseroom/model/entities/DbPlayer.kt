package com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_names")
data class DbPlayer(
    @PrimaryKey
    val name: String
)
