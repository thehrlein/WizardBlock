package com.tobiashehrlein.tobiswizardblock.repositories.repository

import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.cache.GameCache
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.cache.PlayerCache
import com.tobiashehrlein.tobiswizardblock.interactor.repository.GameSettingsRepository

class GameSettingsRepositoryImpl(
    private val playerCache: PlayerCache,
    private val gameCache: GameCache
) : GameSettingsRepository {

    override suspend fun getGameSettings(gameId: Long): AppResult<GameInfo> {
        return gameCache.getGameInfo(gameId)
    }

    override suspend fun getLastGameSettings(): AppResult<GameInfo?> {
        return gameCache.getLastGameInfo()
    }

    override suspend fun getAllPlayerNamesFromCache(): AppResult<Set<String>> {
        return playerCache.getAllPlayerNames()
    }

    override suspend fun storeGameInfo(gameInfo: GameInfo): AppResult<Long> {
        playerCache.addPlayers(gameInfo.players)
        return gameCache.insertGameInfo(gameInfo)
    }

    override suspend fun getGameNameOptions(): AppResult<Set<String>> {
        return gameCache.getGameNameOptions()
    }
}
