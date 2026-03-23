package com.rawg.core.network.helper

import com.rawg.core.common.dispatcher.DispatcherProvider
import com.rawg.core.network.error.NetworkExceptionHandler
import kotlinx.coroutines.withContext

/**
 * Default implementation of [RetrofitHelper].
 *
 * Executes API calls on the IO dispatcher and maps any thrown exceptions
 * to typed [com.rawg.core.network.error.NetworkErrorEntity] via [NetworkExceptionHandler].
 *
 * @param dispatcherProvider Provides coroutine dispatchers for thread switching.
 * @param exceptionHandler Maps raw throwables to typed network errors.
 */
class RetrofitHelperImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val exceptionHandler: NetworkExceptionHandler
) : RetrofitHelper {

    override suspend fun <T> apiExecute(apiCall: suspend () -> T): T {
        return withContext(dispatcherProvider.io) {
            try {
                apiCall()
            } catch (throwable: Throwable) {
                throw exceptionHandler.handle(throwable)
            }
        }
    }
}
