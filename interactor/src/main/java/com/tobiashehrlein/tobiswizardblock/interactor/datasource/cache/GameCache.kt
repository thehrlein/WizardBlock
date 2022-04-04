package com.tobiashehrlein.tobiswizardblock.interactor.datasource.cache

import com.tobiashehrlein.tobiswizardblock.entities.game.general.DeleteRoundData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.entities.game.general.InsertRoundData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult

interface GameCache {

    suspend fun insertGameInfo(gameInfo: GameInfo): AppResult<Long>

    suspend fun getGameInfo(gameId: Long): AppResult<GameInfo>

    suspend fun getLastGameInfo(): AppResult<GameInfo?>

    suspend fun getGame(gameId: Long): AppResult<Game>

    suspend fun insertRound(insertRoundData: InsertRoundData): AppResult<Unit>

    suspend fun removeRound(deleteRoundData: DeleteRoundData): AppResult<Unit>

    suspend fun getAllSavedGames(): AppResult<List<Game>>

    suspend fun deleteGame(gameId: Long): AppResult<Unit>

    suspend fun getGameNameOptions(): AppResult<Set<String>>

    suspend fun deleteALlGames(): AppResult<Unit>
}
