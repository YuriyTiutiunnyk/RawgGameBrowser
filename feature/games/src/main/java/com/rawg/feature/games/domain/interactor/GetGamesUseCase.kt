package com.rawg.feature.games.domain.interactor

import com.rawg.core.domain.interactor.UseCase
import com.rawg.feature.games.domain.model.GamesPage
import com.rawg.feature.games.domain.repository.GamesRepository

/**
 * Parameters for [GetGamesUseCase].
 *
 * @param page The page number to fetch.
 * @param pageSize Number of items per page.
 */
data class GetGamesParams(
    val page: Int,
    val pageSize: Int = PAGE_SIZE
) {
    companion object {
        const val PAGE_SIZE = 20
    }
}

/**
 * Use case for fetching a page of games.
 *
 * Follows the [UseCase] pattern from the core domain module.
 * Wraps pagination parameters in [GetGamesParams] for consistency.
 *
 * @param repository The games repository (with built-in caching).
 */
class GetGamesUseCase(
    private val repository: GamesRepository
) : UseCase<GetGamesParams, GamesPage> {

    override suspend fun invoke(input: GetGamesParams): GamesPage {
        return repository.getGames(input.page, input.pageSize)
    }
}
