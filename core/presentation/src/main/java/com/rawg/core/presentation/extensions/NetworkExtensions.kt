package com.rawg.core.presentation.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rawg.core.network.error.ErrorMapper
import com.rawg.core.network.error.NetworkExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Builder for configuring a network operation using DSL syntax.
 *
 * Eliminates manual try/catch in ViewModels.
 */
class NetworkExecutorBuilder<T> {
    internal var onStartBlock: (suspend () -> Unit)? = null
    internal var executeBlock: (suspend () -> T)? = null
    internal var successBlock: (suspend (T) -> Unit)? = null
    internal var errorBlock: (suspend (String) -> Unit)? = null

    fun onStart(block: suspend () -> Unit) { onStartBlock = block }
    fun execute(block: suspend () -> T) { executeBlock = block }
    fun success(block: suspend (T) -> Unit) { successBlock = block }
    fun error(block: suspend (String) -> Unit) { errorBlock = block }
}

/**
 * Launches a network operation on [viewModelScope] using the DSL builder.
 * Automatically maps exceptions to user-friendly messages.
 */
fun <T> ViewModel.networkExecutor(
    builder: NetworkExecutorBuilder<T>.() -> Unit
): Job {
    val config = NetworkExecutorBuilder<T>().apply(builder)
    val exceptionHandler = NetworkExceptionHandler()
    val errorMapper = ErrorMapper()

    return viewModelScope.launch {
        try {
            config.onStartBlock?.invoke()
            val result = config.executeBlock?.invoke()
                ?: throw IllegalStateException("execute {} block is required")
            config.successBlock?.invoke(result)
        } catch (e: Exception) {
            val networkError = exceptionHandler.handle(e)
            config.errorBlock?.invoke(errorMapper.map(networkError))
        }
    }
}
