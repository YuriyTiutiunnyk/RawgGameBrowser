package com.rawg.feature.games.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * DAO for games list operations.
 */
@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(games: List<GameEntity>)

    @Query("SELECT * FROM games WHERE page = :page ORDER BY orderIndex ASC")
    suspend fun getByPage(page: Int): List<GameEntity>

    @Query("DELETE FROM games")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM games")
    suspend fun count(): Int
}

/**
 * DAO for page pagination info.
 */
@Dao
interface PageInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pageInfo: PageInfoEntity)

    @Query("SELECT * FROM page_info WHERE page = :page")
    suspend fun getByPage(page: Int): PageInfoEntity?

    @Query("DELETE FROM page_info")
    suspend fun clearAll()
}

/**
 * DAO for game detail caching (replaces in-memory cache).
 */
@Dao
interface GameDetailDao {

    @Query("SELECT * FROM game_details WHERE id = :id")
    suspend fun getById(id: Int): GameDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(detail: GameDetailEntity)

    @Query("DELETE FROM game_details")
    suspend fun clearAll()
}
