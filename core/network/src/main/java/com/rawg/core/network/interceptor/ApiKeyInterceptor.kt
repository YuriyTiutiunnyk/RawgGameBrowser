package com.rawg.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * OkHttp interceptor that appends the RAWG API key to every request.
 *
 * The API key is added as a query parameter `key` to all outgoing requests.
 *
 * @param apiKey The RAWG API key obtained from https://rawg.io/apidocs
 */
class ApiKeyInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter("key", apiKey)
            .build()

        val requestWithApiKey = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(requestWithApiKey)
    }
}
