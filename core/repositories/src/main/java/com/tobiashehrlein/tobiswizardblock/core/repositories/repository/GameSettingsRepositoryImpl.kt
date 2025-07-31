package com.tobiashehrlein.tobiswizardblock.core.repositories.repository

import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.cache.GameCache
import com.tobiashehrlein.tobiswizardblock.core.interactor.repository.GameSettingsRepository

class GameSettingsRepositoryImpl(
    private val gameCache: GameCache
) : GameSettingsRepository {

    override suspend fun getGameSettings(gameId: Long): AppResult<GameInfo> {
        return gameCache.getGameInfo(gameId)
    }

    override suspend fun getLastGameSettings(): AppResult<GameInfo?> {
        return gameCache.getLastGameInfo()
    }

    override suspend fun getAllPlayerNamesFromCache(): AppResult<Set<String>> {
        return gameCache.getAllPlayerNames()
    }

    override suspend fun storeGameInfo(gameInfo: GameInfo): AppResult<Long> {
        return gameCache.insertGameInfo(gameInfo)
    }

    override suspend fun getGameNameOptions(): AppResult<Set<String>> {
        return gameCache.getGameNameOptions()
    }
}
