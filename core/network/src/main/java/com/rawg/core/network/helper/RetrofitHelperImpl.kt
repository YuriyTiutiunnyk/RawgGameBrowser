package com.rawg.core.network.helper

import com.rawg.core.common.error.ExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Default implementation of [RetrofitHelper].
 *
 * Executes API calls on the IO dispatcher and maps any thrown exceptions
 * to typed errors via [ExceptionHandler].
 */
class RetrofitHelperImpl(
    private val exceptionHandler: ExceptionHandler
) : RetrofitHelper {

    override suspend fun <T> apiExecute(apiCall: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            try {
                apiCall()
            } catch (throwable: Throwable) {
                throw exceptionHandler.handle(throwable)
            }
        }
    }
}
