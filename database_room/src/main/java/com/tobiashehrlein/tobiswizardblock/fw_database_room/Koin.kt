package com.tobiashehrlein.tobiswizardblock.fw_database_room

import com.tobiashehrlein.tobiswizardblock.fw_database_room.cache.GameCacheImpl
import com.tobiashehrlein.tobiswizardblock.fw_database_room.database.GameDatabase
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.cache.GameCache
import org.koin.dsl.module

val databaseModule = module {
    single { GameDatabase.getInstance(get()) }
    single { get<GameDatabase>().gameDao() }
    single<GameCache> { GameCacheImpl(get()) }
}
