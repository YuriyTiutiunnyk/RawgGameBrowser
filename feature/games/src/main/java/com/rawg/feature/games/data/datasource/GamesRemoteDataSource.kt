package com.rawg.feature.games.data.datasource

import com.rawg.feature.games.data.dto.GameDetailResponseDto
import com.rawg.feature.games.data.dto.GamesListResponseDto

/**
 * Data source interface for remote game data operations.
 *
 * Implementation handles the raw API calls; mapping to domain models is done
 * by the repository.
 */
interface GamesRemoteDataSource {

    /**
     * Fetches a paginated list of games from the remote API.
     *
     * @param page The page number (1-based).
     * @param pageSize The number of results per page.
     * @return Raw [GamesListResponseDto] from the API.
     */
    suspend fun getGames(page: Int, pageSize: Int): GamesListResponseDto

    /**
     * Fetches detailed information for a specific game.
     *
     * @param id The unique game identifier.
     * @return Raw [GameDetailResponseDto] from the API.
     */
    suspend fun getGameDetail(id: Int): GameDetailResponseDto
}
