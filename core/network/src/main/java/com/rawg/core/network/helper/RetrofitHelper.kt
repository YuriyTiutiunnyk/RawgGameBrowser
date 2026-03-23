package com.rawg.core.network.helper

/**
 * Abstraction for executing Retrofit API calls with centralized error handling.
 *
 * Wraps raw API calls and converts exceptions to typed [com.rawg.core.network.error.NetworkErrorEntity].
 */
interface RetrofitHelper {

    /**
     * Executes an API call and returns the response body, or throws a [NetworkErrorEntity]
     * on failure.
     *
     * @param apiCall The suspend lambda that performs the Retrofit API call.
     * @return The deserialized response body of type [T].
     * @throws com.rawg.core.network.error.NetworkErrorEntity on any network failure.
     */
    suspend fun <T> apiExecute(apiCall: suspend () -> T): T
}
