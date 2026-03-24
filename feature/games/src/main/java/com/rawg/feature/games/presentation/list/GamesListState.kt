package com.rawg.feature.games.presentation.list

import com.rawg.core.common.result.DataState
import com.rawg.core.presentation.contract.UiState
import com.rawg.feature.games.domain.model.Game

data class GamesListState(
    val games: DataState<List<Game>> = DataState.Loading
) : UiState
