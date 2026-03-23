package com.rawg.core.network.error

/**
 * Sealed hierarchy of network errors.
 *
 * Provides type-safe error categorization for network operations.
 *
 * Extends [Exception] (not [Throwable]) so it is catchable by standard
 * `catch (e: Exception)` blocks in PagingSource, ViewModels, and Flow operators.
 *
 * Each subclass represents a specific failure scenario, allowing the presentation
 * layer to display appropriate user-friendly messages.
 */
sealed class NetworkErrorEntity(
    override val message: String,
    cause: Throwable? = null
) : Exception(message, cause) {

    /** Device has no internet connection. */
    class NoConnectionError(
        cause: Throwable? = null
    ) : NetworkErrorEntity("No internet connection", cause)

    /** Server returned an error response (5xx). */
    class ServerError(
        val code: Int,
        serverMessage: String? = null,
        cause: Throwable? = null
    ) : NetworkErrorEntity(serverMessage ?: "Server error ($code)", cause)

    /** Request timed out. */
    class TimeoutError(
        cause: Throwable? = null
    ) : NetworkErrorEntity("Connection timed out", cause)

    /** HTTP error response (4xx, excluding specific cases). */
    class HttpError(
        val code: Int,
        httpMessage: String? = null,
        cause: Throwable? = null
    ) : NetworkErrorEntity(httpMessage ?: "HTTP error ($code)", cause)

    /** An unexpected error occurred. */
    class UnknownError(
        unknownMessage: String? = null,
        cause: Throwable? = null
    ) : NetworkErrorEntity(unknownMessage ?: "An unexpected error occurred", cause)
}
