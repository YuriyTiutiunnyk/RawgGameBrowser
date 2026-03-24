package com.rawg.core.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rawg.core.presentation.contract.UiEvent
import com.rawg.core.presentation.contract.UiState
import com.rawg.core.presentation.vm.BaseVm

/**
 * Base screen wrapper for Compose screens.
 *
 * Centralizes:
 * - State collection via [collectAsStateWithLifecycle]
 * - Event delegation via `onEvent` callback
 *
 * Screens only implement the `content` lambda — `(state, onEvent)`.
 * Navigation is handled externally by feature NavGraph callbacks.
 */
@Composable
fun <UIState : UiState, UIEvent : UiEvent> BaseScreen(
    viewModel: BaseVm<UIState, UIEvent>,
    content: @Composable (state: UIState, onEvent: (UIEvent) -> Unit) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    content(state, viewModel::handleEvent)
}
