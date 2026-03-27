package com.rawg.feature.favorites.presentation

import com.rawg.core.common.result.DataState
import com.rawg.core.presentation.extensions.NetworkExecutor
import com.rawg.core.presentation.vm.BaseVm
import com.rawg.feature.favorites.presentation.model.FavoriteGame

class FavoritesVm(
    networkExecutor: NetworkExecutor
) : BaseVm<FavoritesState, FavoritesEvent>(FavoritesState(), networkExecutor) {

    init {
        loadFavorites()
    }

    override fun handleEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.Refresh -> loadFavorites()
        }
    }

    private fun loadFavorites() {
        val mockGames = listOf(
            FavoriteGame(id = 3498, name = "Grand Theft Auto V", rating = 4.47),
            FavoriteGame(id = 3328, name = "The Witcher 3: Wild Hunt", rating = 4.66),
            FavoriteGame(id = 5286, name = "Tomb Raider", rating = 4.05),
            FavoriteGame(id = 4200, name = "Portal 2", rating = 4.61),
            FavoriteGame(id = 5679, name = "The Elder Scrolls V: Skyrim", rating = 4.42)
        )
        updateState { copy(favorites = DataState.Success(mockGames)) }
    }
}
