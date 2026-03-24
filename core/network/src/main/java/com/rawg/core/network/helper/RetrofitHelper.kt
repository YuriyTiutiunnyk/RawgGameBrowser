package com.rawg.core.network.helper

/**
 * Abstraction for executing Retrofit API calls with centralized error handling.
 *
 * Wraps raw API calls and converts exceptions to typed [com.rawg.core.common.error.ErrorEntity].
 */
interface RetrofitHelper {

    /**
     * Executes an API call and returns the response body, or throws an [com.rawg.core.common.error.ErrorEntity]
     * on failure.
     *
     * @param apiCall The suspend lambda that performs the Retrofit API call.
     * @return The deserialized response body of type [T].
     * @throws com.rawg.core.common.error.ErrorEntity on any network failure.
     */
    suspend fun <T> apiExecute(apiCall: suspend () -> T): T
}
