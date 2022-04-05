package com.tobiashehrlein.tobiswizardblock.fw_database_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.tobiashehrlein.tobiswizardblock.fw_database_room.model.classes.DbGame
import com.tobiashehrlein.tobiswizardblock.fw_database_room.model.classes.DbGameRound
import com.tobiashehrlein.tobiswizardblock.fw_database_room.model.entities.DbGameInfo

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameInfo(dbGameInfo: DbGameInfo): Long

    @Query("SELECT * FROM GAME_DATABASE WHERE gameId = :gameId")
    fun getGameInfo(gameId: Long): DbGameInfo

    @Query("SELECT * FROM GAME_DATABASE ORDER BY gameId DESC LIMIT 1")
    fun getLastGameInfo(): DbGameInfo?

    @Transaction
    @Query("SELECT * FROM GAME_DATABASE WHERE gameId = :gameId")
    fun getGame(gameId: Long): DbGame?

    @Query("SELECT * FROM GAME_ROUNDS WHERE gameId = :gameId")
    fun getRounds(gameId: Long): List<DbGameRound>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRound(dbRound: DbGameRound): Long

    @Query("DELETE FROM GAME_ROUNDS WHERE gameId = :gameId AND round = :round")
    fun removeRound(gameId: Long, round: Int)

    @Query("SELECT * FROM GAME_DATABASE ORDER BY gameStartDate DESC")
    fun getAllSavedGames(): List<DbGame>

    @Transaction
    fun deleteGame(gameId: Long) {
        deleteGameInfo(gameId)
        deleteGameRounds(gameId)
    }

    @Query("DELETE FROM GAME_DATABASE WHERE gameId = :gameId")
    fun deleteGameInfo(gameId: Long)

    @Query("DELETE FROM GAME_ROUNDS WHERE gameId = :gameId")
    fun deleteGameRounds(gameId: Long)

    @Query("SELECT gameName from game_database")
    fun getGameNameOptions(): List<String>?

    @Transaction
    fun deleteAllGames() {
        deleteAllGameInfo()
        deleteAllGameRounds()
    }

    @Query("DELETE FROM GAME_DATABASE")
    fun deleteAllGameInfo()

    @Query("DELETE FROM GAME_ROUNDS")
    fun deleteAllGameRounds()
}
