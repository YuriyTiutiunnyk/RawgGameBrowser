package com.rawg.feature.favorites.presentation

import com.rawg.core.presentation.contract.UiEvent

sealed interface FavoritesEvent : UiEvent {
    data object Refresh : FavoritesEvent
}
