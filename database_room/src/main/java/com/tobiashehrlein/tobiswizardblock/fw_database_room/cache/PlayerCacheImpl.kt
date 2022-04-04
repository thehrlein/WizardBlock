package com.tobiashehrlein.tobiswizardblock.fw_database_room.cache

import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.fw_database_room.dao.PlayerDao
import com.tobiashehrlein.tobiswizardblock.fw_database_room.model.entities.DbPlayer
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.BaseDatasource
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.cache.PlayerCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerCacheImpl(
    private val playerDao: PlayerDao
) : BaseDatasource,
    PlayerCache {

    override suspend fun addPlayers(names: List<String>): AppResult<Unit> =
        withContext(Dispatchers.IO) {
            safeCall {
                playerDao.insertPlayerNames(names.map {
                    DbPlayer(
                        it
                    )
                })
            }
        }

    override suspend fun getAllPlayerNames(): AppResult<Set<String>> =
        withContext(Dispatchers.IO) {
            safeCall {
                playerDao.queryAllPlayerNames().map {
                    it.name
                }.toSet()
            }
        }
}
