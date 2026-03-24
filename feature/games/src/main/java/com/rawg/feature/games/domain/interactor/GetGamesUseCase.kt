package com.rawg.feature.games.domain.interactor

import com.rawg.feature.games.domain.model.GamesPage
import com.rawg.feature.games.domain.repository.GamesRepository

class GetGamesUseCase(
    private val repository: GamesRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int = PAGE_SIZE): GamesPage {
        return repository.getGames(page, pageSize)
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
