package com.rawg.core.presentation.contract

/**
 * Marker interface for UI events (user actions / intents).
 *
 * All screen event sealed classes/interfaces must implement this interface.
 *
 * Usage:
 * ```kotlin
 * sealed interface GamesListEvent : UiEvent {
 *     data class OnGameClicked(val id: Int) : GamesListEvent
 *     data object OnRetry : GamesListEvent
 * }
 * ```
 */
interface UiEvent
