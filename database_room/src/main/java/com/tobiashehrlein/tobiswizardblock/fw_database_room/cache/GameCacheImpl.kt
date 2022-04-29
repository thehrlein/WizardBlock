package com.tobiashehrlein.tobiswizardblock.fw_database_room.cache

import com.tobiashehrlein.tobiswizardblock.entities.game.general.DeleteRoundData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.entities.game.general.InsertRoundData
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.statistics.GameDayStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.GameRulesStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.entities.statistics.TopPointsStatisticsData
import com.tobiashehrlein.tobiswizardblock.fw_database_room.dao.GameDao
import com.tobiashehrlein.tobiswizardblock.fw_database_room.model.mapper.mapToDbData
import com.tobiashehrlein.tobiswizardblock.fw_database_room.model.mapper.mapToEntity
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.BaseDatasource
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.cache.GameCache
import com.tobiashehrlein.tobiswizardblock.old.utils.helper.DateHelper
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

    override suspend fun getAllPlayerNames(): AppResult<Set<String>> = withContext(Dispatchers.IO) {
        safeCall {
            gameDao.getAllGameInfo().flatMap {
                it.players
            }.toSet()
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
                gameDao.getAllGames().filter {
                    !it.gameInfo.removedFromSavedGames
                }.map {
                    it.mapToEntity()
                }
            }
        }

    override suspend fun removeGameFromSavedGames(gameId: Long): AppResult<Unit> =
        withContext(Dispatchers.IO) {
            safeCall {
                val gameInfo = gameDao.getGameInfo(gameId).copy(
                    removedFromSavedGames = true
                )
                gameDao.insertGameInfo(gameInfo)
                Unit
            }
        }

    override suspend fun removeAllGamesFromSavedGames(): AppResult<Unit> =
        withContext(Dispatchers.IO) {
            safeCall {
                val gameInfo = gameDao.getAllGameInfo()?.map {
                    it.copy(
                        removedFromSavedGames = true
                    )
                } ?: emptyList()
                gameDao.insertAllGameInfo(gameInfo)
                Unit
            }
        }

    override suspend fun getGameNameOptions(): AppResult<Set<String>> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.getGameNameOptions()?.toSet() ?: emptySet()
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

    override suspend fun getPlayerCountStatistics(): AppResult<Map<Int, Int>> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.getAllGames().map { it.mapToEntity() }.map { it.gameInfo.players.size }
                    .groupingBy { it }.eachCount()
            }
        }

    override suspend fun getTopPointsStatistics(): AppResult<List<TopPointsStatisticsData>> =
        withContext(Dispatchers.IO) {
            safeCall {
                val totals =
                    gameDao.getAllFinishedGames().map { it.mapToEntity() }.mapNotNull { game ->
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

    override suspend fun getGamesPlayedCountStatistics(): AppResult<Int> =
        withContext(Dispatchers.IO) {
            safeCall {
                gameDao.getAllFinishedGames().size
            }
        }

    override suspend fun getGameDayStatistics(): AppResult<GameDayStatisticsData> =
        withContext(Dispatchers.IO) {
            safeCall {
                val gameDays = gameDao.getAllGames()
                    .map { it.mapToEntity() }
                    .map { it.gameInfo.gameStartDate }
                    .map { DateHelper.getDayOfWeek(it) }
                    .groupingBy { it }.eachCount()
                    .toSortedMap()

                GameDayStatisticsData(
                    gameDays = gameDays
                )
            }
        }

    override suspend fun getGameRulesStatistics(): AppResult<GameRulesStatisticsData> =
        withContext(Dispatchers.IO) {
            safeCall {
                val gameSettings = gameDao.getAllGames()
                    .map { it.mapToEntity() }
                    .map { it.gameInfo.gameSettings }

                GameRulesStatisticsData(
                    noGamesPlayed = gameSettings.isEmpty(),
                    tipsEqualStitches = gameSettings.count { it.tipsEqualStitches },
                    tipsEqualStitchesFirstRound = gameSettings.count { it.tipsEqualStitchesFirstRound },
                    anniversaryVersion = gameSettings.count { it.anniversaryVersion }
                )
            }
        }

    override suspend fun clearStatistics(): AppResult<Unit> = withContext(Dispatchers.IO) {
        safeCall {
            gameDao.clearStatistics()
        }
    }
}
