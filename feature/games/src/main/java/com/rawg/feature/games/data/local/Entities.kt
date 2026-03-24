package com.rawg.feature.games.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for cached games in the list.
 * Serves as the single source of truth for the games list.
 */
@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String?,
    val rating: Double,
    val page: Int,
    val orderIndex: Int
)

/**
 * Room entity for cached game details.
 * Replaces the old in-memory GameDetailCache.
 * List fields are stored as JSON via [StringListConverter].
 */
@Entity(tableName = "game_details")
data class GameDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String?,
    val rating: Double,
    val description: String,
    val released: String?,
    val metacritic: Int?,
    val genres: List<String>,
    val platforms: List<String>,
    val developers: List<String>,
    val publishers: List<String>,
    val website: String?,
    val esrbRating: String?,
    val ratingsCount: Int,
    val playtime: Int
)

/**
 * Room entity for tracking pagination state per page.
 * Stores whether a given page has more data to load.
 */
@Entity(tableName = "page_info")
data class PageInfoEntity(
    @PrimaryKey val page: Int,
    val hasNextPage: Boolean
)
