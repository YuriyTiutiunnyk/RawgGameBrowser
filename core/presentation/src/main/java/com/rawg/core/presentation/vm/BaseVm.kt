package com.rawg.core.presentation.vm

import androidx.lifecycle.ViewModel
import com.rawg.core.presentation.contract.UiEvent
import com.rawg.core.presentation.contract.UiState
import com.rawg.core.presentation.extensions.NetworkExecutor
import com.rawg.core.presentation.extensions.NetworkExecutorBuilder
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Base ViewModel following the MVI-flavored MVVM pattern.
 *
 * Provides:
 * - Type-safe UI state management via [StateFlow]
 * - Abstract event handling via [handleEvent]
 * - State update helper via [updateState]
 * - [networkExecutor] DSL for async operations
 *
 * @param initialState The initial UI state.
 * @param networkExecutor Singleton executor for network operations, injected via Koin DI.
 */
abstract class BaseVm<UIState : UiState, UIEvent : UiEvent>(
    initialState: UIState,
    private val networkExecutor: NetworkExecutor
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<UIState> = _state.asStateFlow()

    abstract fun handleEvent(event: UIEvent)

    protected fun <T> networkExecutor(
        builder: NetworkExecutorBuilder<T>.() -> Unit
    ): Job = with(networkExecutor) { execute(builder) }

    protected fun updateState(reducer: UIState.() -> UIState) {
        _state.value = _state.value.reducer()
    }

    protected fun getState(): UIState = _state.value
}
