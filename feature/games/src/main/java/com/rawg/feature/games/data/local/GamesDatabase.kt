package com.rawg.feature.games.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [GameEntity::class, GameDetailEntity::class, PageInfoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class GamesDatabase : RoomDatabase() {
    abstract fun gamesDao(): GamesDao
    abstract fun pageInfoDao(): PageInfoDao
    abstract fun gameDetailDao(): GameDetailDao
}
