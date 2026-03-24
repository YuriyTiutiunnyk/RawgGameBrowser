package com.rawg.feature.games.presentation.list

import com.rawg.core.presentation.contract.UiEvent

/**
 * UI events for the Games List screen.
 *
 * Uses the sealed interface pattern for type-safe event handling.
 */
sealed interface GamesListEvent : UiEvent {

    /** User scrolled near the end — load next page. */
    data object LoadMore : GamesListEvent

    /** User tapped retry on initial load error. */
    data object Retry : GamesListEvent

    /** User tapped retry on load-more error. */
    data object RetryLoadMore : GamesListEvent
}