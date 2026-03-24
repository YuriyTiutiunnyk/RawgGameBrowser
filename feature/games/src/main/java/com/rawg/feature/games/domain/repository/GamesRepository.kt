package com.rawg.feature.games.domain.repository

import com.rawg.feature.games.domain.model.GameDetail
import com.rawg.feature.games.domain.model.GamesPage

/**
 * Repository interface for game data operations.
 *
 * Defined in the domain layer per Clean Architecture principles.
 * Implementation resides in the data layer.
 */
interface GamesRepository {

    /**
     * Fetches a page of games.
     *
     * Network-first with Room fallback on error.
     */
    suspend fun getGames(page: Int, pageSize: Int): GamesPage

    /**
     * Fetches detailed information for a specific game.
     *
     * Room-first, network on cache miss.
     */
    suspend fun getGameDetail(id: Int): GameDetail
}
