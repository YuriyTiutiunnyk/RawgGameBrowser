package com.rawg.feature.favorites.presentation

import com.rawg.core.common.result.DataState
import com.rawg.core.presentation.contract.UiState
import com.rawg.feature.favorites.presentation.model.FavoriteGame

data class FavoritesState(
    val favorites: DataState<List<FavoriteGame>> = DataState.Loading
) : UiState
