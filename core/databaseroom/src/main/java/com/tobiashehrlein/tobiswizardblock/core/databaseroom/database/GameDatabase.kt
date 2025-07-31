package com.tobiashehrlein.tobiswizardblock.core.databaseroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.dao.GameDao
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes.DbGameRound
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.converter.DbPlayerListConverter
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.converter.DbPlayerResultDataListConverter
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.converter.DbPlayerTipDataListConverter
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.converter.DbTrumpTypeConverter
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.entities.DbGameInfo

@Database(
    entities = [
        DbGameInfo::class,
        DbGameRound::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        DbPlayerListConverter::class,
        DbPlayerResultDataListConverter::class,
        DbPlayerTipDataListConverter::class,
        DbTrumpTypeConverter::class
    ]
)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getInstance(context: Context): GameDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
