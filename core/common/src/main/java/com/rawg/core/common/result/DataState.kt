package com.rawg.core.common.result

/**
 * Represents the state of an asynchronous data operation.
 *
 * Single source of truth for Loading/Success/Error/Empty states.
 * Used in UiState to drive screen rendering via when-expression.
 */
sealed interface DataState<out T> {
    data object Loading : DataState<Nothing>
    data class Success<out T>(val data: T) : DataState<T>
    data class Error(val message: String) : DataState<Nothing>
    data object Empty : DataState<Nothing>
}
