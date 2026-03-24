package com.rawg.feature.games.presentation.detail

import com.rawg.core.presentation.contract.UiState
import com.rawg.feature.games.domain.model.GameDetail

/**
 * UI state for the Game Detail screen.
 *
 * Manages loading, data, and error states following the MVI pattern.
 */
data class GameDetailState(
    val isLoading: Boolean = true,
    val gameDetail: GameDetail? = null,
    val error: String? = null
) : UiState
