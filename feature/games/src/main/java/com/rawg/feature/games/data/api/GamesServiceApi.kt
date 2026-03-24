package com.rawg.feature.games.data.api

import com.rawg.feature.games.data.dto.GameDetailResponseDto
import com.rawg.feature.games.data.dto.GamesListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API interface for the RAWG Video Games Database.
 *
 * Endpoints:
 * - GET /games — Paginated list of games
 * - GET /games/{id} — Detailed information for a single game
 *
 * API documentation: https://rawg.io/apidocs
 */
interface GamesServiceApi {

    /**
     * Fetches a paginated list of games.
     *
     * @param page The page number to fetch (1-based).
     * @param pageSize The number of results per page.
     * @return [GamesListResponseDto] containing the list of games and pagination info.
     */
    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): GamesListResponseDto

    /**
     * Fetches detailed information for a specific game.
     *
     * @param id The unique identifier of the game.
     * @return [GameDetailResponseDto] containing full game details.
     */
    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: Int
    ): GameDetailResponseDto
}
