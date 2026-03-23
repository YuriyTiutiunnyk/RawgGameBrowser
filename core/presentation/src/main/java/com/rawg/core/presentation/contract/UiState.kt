package com.rawg.core.presentation.contract

/**
 * Marker interface for UI state classes.
 *
 * All screen state data classes must implement this interface.
 *
 * Usage:
 * ```kotlin
 * data class GamesListState(
 *     val isLoading: Boolean = false,
 *     val error: String? = null
 * ) : UiState
 * ```
 */
interface UiState
