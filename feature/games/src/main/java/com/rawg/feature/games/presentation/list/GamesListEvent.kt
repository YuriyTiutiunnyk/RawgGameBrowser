package com.rawg.feature.games.presentation.list

import com.rawg.core.presentation.contract.UiEvent

sealed interface GamesListEvent : UiEvent {
    data object Retry : GamesListEvent
}
