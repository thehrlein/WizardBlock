package com.tobiashehrlein.tobiswizardblock.core.databaseroom

import com.tobiashehrlein.tobiswizardblock.core.databaseroom.cache.GameCacheImpl
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.database.GameDatabase
import com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.cache.GameCache
import org.koin.dsl.module

val databaseModule = module {
    single { GameDatabase.getInstance(get()) }
    single { get<GameDatabase>().gameDao() }
    single<GameCache> { GameCacheImpl(get()) }
}
