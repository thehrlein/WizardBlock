package com.tobiashehrlein.tobiswizardblock.interactor.datasource.cache

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult

interface PlayerCache {

    suspend fun addPlayers(names: List<String>): AppResult<Unit>

    suspend fun getAllPlayerNames(): AppResult<Set<String>>
}
