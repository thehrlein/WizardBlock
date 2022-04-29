package com.tobiashehrlein.tobiswizardblock.interactor.repository

import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult

interface GameSettingsRepository {

    suspend fun getGameSettings(gameId: Long): AppResult<GameInfo>

    suspend fun getLastGameSettings(): AppResult<GameInfo?>

    suspend fun getAllPlayerNamesFromCache(): AppResult<Set<String>>

    suspend fun storeGameInfo(gameInfo: GameInfo): AppResult<Long>

    suspend fun getGameNameOptions(): AppResult<Set<String>>
}
