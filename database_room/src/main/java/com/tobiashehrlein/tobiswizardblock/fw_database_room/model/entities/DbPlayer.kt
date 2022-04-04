package com.tobiashehrlein.tobiswizardblock.fw_database_room.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_names")
data class DbPlayer(
    @PrimaryKey
    val name: String
)
