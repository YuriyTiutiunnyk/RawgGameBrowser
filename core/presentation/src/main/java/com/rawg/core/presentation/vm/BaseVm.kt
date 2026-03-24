package com.rawg.core.presentation.vm

import androidx.lifecycle.ViewModel
import com.rawg.core.presentation.contract.UiEvent
import com.rawg.core.presentation.contract.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseVm<UIState : UiState, UIEvent : UiEvent>(
    initialState: UIState
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<UIState> = _state.asStateFlow()

    abstract fun handleEvent(event: UIEvent)

    open fun onCreate() {}

    protected fun updateState(reducer: UIState.() -> UIState) {
        _state.value = _state.value.reducer()
    }

    protected fun getState(): UIState = _state.value
}
