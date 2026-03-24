package com.rawg.feature.games.domain.interactor

import com.rawg.core.domain.interactor.UseCase
import com.rawg.feature.games.domain.model.GameDetail
import com.rawg.feature.games.domain.repository.GamesRepository

/**
 * Use case for fetching detailed information about a specific game.
 *
 * Follows the [UseCase] pattern from the core domain module.
 * Leverages the repository's cache-first strategy for optimal performance.
 *
 * @param repository The games repository (with built-in caching).
 */
class GetGameDetailUseCase(
    private val repository: GamesRepository
) : UseCase<Int, GameDetail> {

    /**
     * Fetches game detail by ID. Returns cached data if available.
     *
     * @param input The unique game identifier.
     * @return [GameDetail] domain model.
     */
    override suspend fun invoke(input: Int): GameDetail {
        return repository.getGameDetail(input)
    }
}
