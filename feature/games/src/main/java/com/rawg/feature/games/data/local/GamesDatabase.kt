package com.rawg.feature.games.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Room database for the Games feature.
 *
 * Contains tables for:
 * - [GameEntity] — cached games list (single source of truth)
 * - [GameDetailEntity] — cached game details (persistent across app restarts)
 * - [PageInfoEntity] — pagination state per page
 *
 * Uses [StringListConverter] for storing `List<String>` fields as JSON.
 */
@Database(
    entities = [
        GameEntity::class,
        GameDetailEntity::class,
        PageInfoEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class GamesDatabase : RoomDatabase() {
    abstract fun gamesDao(): GamesDao
    abstract fun pageInfoDao(): PageInfoDao
    abstract fun gameDetailDao(): GameDetailDao
}
