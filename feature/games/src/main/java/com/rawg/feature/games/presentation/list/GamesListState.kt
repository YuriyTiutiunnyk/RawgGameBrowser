package com.rawg.feature.games.presentation.list

import com.rawg.core.common.result.DataState
import com.rawg.core.presentation.contract.UiState
import com.rawg.feature.games.domain.model.Game

/**
 * UI state for the Games List screen.
 *
 * Single source of truth — all data including pagination state.
 */
data class GamesListState(
    val games: DataState<List<Game>> = DataState.Loading,
    val hasMoreData: Boolean = true,
    val isLoadingMore: Boolean = false,
    val loadMoreError: String? = null
) : UiState
