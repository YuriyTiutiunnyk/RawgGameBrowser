package com.rawg.feature.games.domain.model

import com.rawg.core.common.annotation.DomainModel

/**
 * Domain model representing a game in the list view.
 *
 * Contains only the fields needed for the games list screen.
 * Mapped from [com.rawg.feature.games.data.local.GameEntity] via
 * [com.rawg.feature.games.data.mapper.EntityMappers].
 */
@DomainModel
data class Game(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val rating: Double
)

/**
 * Paginated response wrapper for games list.
 *
 * Used by ViewModel for manual pagination — tracks whether more pages exist.
 */
@DomainModel
data class GamesPage(
    val games: List<Game>,
    val hasNextPage: Boolean
)

/**
 * Domain model representing detailed information about a game.
 *
 * Contains all fields needed for the game detail screen.
 * Mapped from [com.rawg.feature.games.data.dto.GameDetailResponseDto] via
 * [com.rawg.feature.games.data.mapper.GameDetailMapper].
 */
@DomainModel
data class GameDetail(
    val id: Int,
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
