package com.tobiashehrlein.tobiswizardblock.fw_database_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tobiashehrlein.tobiswizardblock.fw_database_room.model.entities.DbPlayer

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayerNames(dbPlayer: List<DbPlayer>)

    @Query("SELECT * FROM player_names")
    fun queryAllPlayerNames(): List<DbPlayer>
}
