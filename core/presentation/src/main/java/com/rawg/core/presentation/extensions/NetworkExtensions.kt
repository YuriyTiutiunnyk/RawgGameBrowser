package com.rawg.core.presentation.extensions

import androidx.lifecycle.viewModelScope
import com.rawg.core.common.error.ErrorMessageMapper
import com.rawg.core.common.error.ExceptionHandler
import com.rawg.core.presentation.vm.BaseVm
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * Builder class for configuring a network operation using DSL syntax.
 *
 * Eliminates manual try/catch in ViewModels. Provides declarative callbacks
 * for each lifecycle phase of a network call.
 *
 * Usage:
 * ```kotlin
 * networkExecutor<GameDetail> {
 *     onStart  { updateState { copy(isLoading = true, error = null) } }
 *     execute  { getGameDetailUseCase(gameId) }
 *     success  { detail -> updateState { copy(isLoading = false, gameDetail = detail) } }
 *     error    { msg -> updateState { copy(isLoading = false, error = msg) } }
 * }
 * ```
 */
class NetworkExecutorBuilder<T> {
    internal var onStartBlock: (suspend () -> Unit)? = null
    internal var executeBlock: (suspend () -> T)? = null
    internal var successBlock: (suspend (T) -> Unit)? = null
    internal var errorBlock: (suspend (String) -> Unit)? = null

    /** Called before the network request starts. Ideal for showing loading state. */
    fun onStart(block: suspend () -> Unit) { onStartBlock = block }

    /** The actual suspend operation to execute (use case / repository call). */
    fun execute(block: suspend () -> T) { executeBlock = block }

    /** Called on successful execution with the result data. */
    fun success(block: suspend (T) -> Unit) { successBlock = block }

    /** Called on failure with a user-friendly error message. */
    fun error(block: suspend (String) -> Unit) { errorBlock = block }
}

/**
 * Singleton executor for network operations.
 *
 * Holds shared dependencies
 * (exception handler, error mapper) as singletons via Koin DI
 *
 * @param exceptionHandler Classifies raw exceptions into typed [com.rawg.core.common.error.ErrorEntity].
 * @param errorMapper Converts errors into messages.
 */
class NetworkExecutor(
    private val exceptionHandler: ExceptionHandler,
    private val errorMapper: ErrorMessageMapper
) {
    /**
     * Launches a network operation on [viewModelScope] using the DSL builder.
     *
     * @return [Job] that can be cancelled if needed.
     */
    fun <T> BaseVm<*, *>.execute(
        builder: NetworkExecutorBuilder<T>.() -> Unit
    ): Job {
        val config = NetworkExecutorBuilder<T>().apply(builder)

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
}
