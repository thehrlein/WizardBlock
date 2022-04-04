package com.tobiashehrlein.tobiswizardblock.fw_database_room.cache

import com.tobiashehrlein.tobiswizardblock.entities.game.general.DeleteRoundData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.entities.game.general.InsertRoundData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.fw_database_room.dao.GameDao
import com.tobiashehrlein.tobiswizardblock.fw_database_room.model.mapper.mapToDbData
import com.tobiashehrlein.tobiswizardblock.fw_database_room.model.mapper.mapToEntity
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.BaseDatasource
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.cache.GameCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameCacheImpl(
    private val gameDao: GameDao
) : BaseDatasource, GameCache {

    override suspend fun insertGameInfo(gameInfo: GameInfo): AppResult<Long> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.insertGameInfo(gameInfo.mapToDbData())
            }
        }

    override suspend fun getGameInfo(gameId: Long): AppResult<GameInfo> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.getGameInfo(gameId).mapToEntity()
            }
        }

    override suspend fun getLastGameInfo(): AppResult<GameInfo?> = withContext(Dispatchers.IO) {
        safeCall {
            gameDao.getLastGameInfo()?.mapToEntity()
        }
    }

    override suspend fun getGame(gameId: Long): AppResult<Game> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.getGame(gameId)?.mapToEntity()!!
            }
        }

    override suspend fun insertRound(insertRoundData: InsertRoundData): AppResult<Unit> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.insertRound(insertRoundData.gameRound.mapToDbData(insertRoundData.gameId))
                Unit
            }
        }

    override suspend fun removeRound(deleteRoundData: DeleteRoundData): AppResult<Unit> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.removeRound(
                    gameId = deleteRoundData.gameId,
                    round = deleteRoundData.gameRound.round
                )
            }
        }

    override suspend fun getAllSavedGames(): AppResult<List<Game>> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.getAllSavedGames().map {
                    it.mapToEntity()
                }
            }
        }

    override suspend fun deleteGame(gameId: Long): AppResult<Unit> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.deleteGame(gameId)
            }
        }

    override suspend fun getGameNameOptions(): AppResult<Set<String>> = withContext(Dispatchers.IO) {
        safeCall {
            gameDao.getGameNameOptions()?.toSet() ?: emptySet()
        }
    }

    override suspend fun deleteALlGames(): AppResult<Unit> = withContext(Dispatchers.IO) {
        safeCall {
            gameDao.deleteAllGames()
        }
    }
}
