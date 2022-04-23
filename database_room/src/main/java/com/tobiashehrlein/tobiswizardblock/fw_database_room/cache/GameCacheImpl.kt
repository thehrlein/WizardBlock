package com.tobiashehrlein.tobiswizardblock.fw_database_room.cache

import com.tobiashehrlein.tobiswizardblock.entities.game.general.DeleteRoundData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.entities.game.general.InsertRoundData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.TopPointsStatisticsData
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

    override suspend fun getGameNameOptions(): AppResult<Set<String>> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.getGameNameOptions()?.toSet() ?: emptySet()
            }
        }

    override suspend fun deleteALlGames(): AppResult<Unit> = withContext(Dispatchers.IO) {
        safeCall {
            gameDao.deleteAllGames()
        }
    }

    override suspend fun getMostWinsStatistics(): AppResult<List<MostWinStatisticsData>> =
        withContext(Dispatchers.IO) {
            safeCall {
                val winner = gameDao.getAllFinishedGames().map {
                    it.mapToEntity()
                }.mapNotNull {
                    it.winner
                }.groupingBy {
                    it
                }.eachCount()

                val winnerMap = mutableMapOf<Int, MutableList<String>>()
                winner.entries.forEach { entry ->
                    winnerMap.getOrPut(entry.value) {
                        mutableListOf()
                    }.add(entry.key)
                }

                winnerMap.entries.forEach { entry ->
                    entry.value.sortBy { it.lowercase() }
                }

                winnerMap.entries.flatMapIndexed { index, entry ->
                    entry.value.map {
                        MostWinStatisticsData(
                            position = index + 1,
                            playerName = it,
                            wins = entry.key
                        )
                    }
                }.take(10)
            }
        }

    override suspend fun getPlayerCountStatistics(): AppResult<Map<Int, Int>> = withContext(Dispatchers.IO) {
        safeCall {
            gameDao.getAllSavedGames().map { it.mapToEntity() }.map { it.gameInfo.players.size }.groupingBy { it }.eachCount()
        }
    }

    override suspend fun getTopPointsStatistics(): AppResult<List<TopPointsStatisticsData>> = withContext(Dispatchers.IO) {
        safeCall {
            val totals = gameDao.getAllFinishedGames().map { it.mapToEntity() }.mapNotNull { game ->
                game.lastCompletedGameRound?.playerResultData?.map { playerResultData ->
                    playerResultData.total to playerResultData.playerName
                }
            }.flatten()
            val totalsMap = mutableMapOf<Int, MutableList<String>>()
            totals.forEach { pair ->
                totalsMap.getOrPut(pair.first) {
                    mutableListOf()
                }.add(pair.second)
            }

            val sortedMap = totalsMap.toSortedMap(compareByDescending { it })

            sortedMap.entries.flatMapIndexed { index, entry ->
                entry.value.map {
                    TopPointsStatisticsData(
                        position = index + 1,
                        playerName = it,
                        points = entry.key
                    )
                }
            }.take(10)
        }
    }
}
