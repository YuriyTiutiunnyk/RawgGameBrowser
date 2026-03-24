package com.rawg.feature.games.presentation.detail

import com.rawg.core.presentation.contract.UiEvent

/**
 * UI events for the Game Detail screen.
 */
sealed interface GameDetailEvent : UiEvent {

    /** User tapped retry after an error. */
    data object OnRetry : GameDetailEvent
}
