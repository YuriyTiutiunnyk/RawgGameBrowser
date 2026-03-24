package com.rawg.core.common.error

/**
 * Sealed hierarchy of application errors.
 *
 * Provides type-safe error categorization for network and other operations.
 * Lives in `core:common` so both `core:network` (producer) and
 * `core:presentation` (consumer) can depend on it without a direct
 * dependency between each other — enforcing the Clean Architecture dependency rule.
 *
 * Extends [Exception] so it is catchable by standard `catch (e: Exception)` blocks.
 */
sealed class ErrorEntity(
    override val message: String,
    cause: Throwable? = null
) : Exception(message, cause) {

    /** Device has no internet connection. */
    class NoConnection(
        cause: Throwable? = null
    ) : ErrorEntity("No internet connection", cause)

    /** Server returned an error response (5xx). */
    class Server(
        val code: Int,
        serverMessage: String? = null,
        cause: Throwable? = null
    ) : ErrorEntity(serverMessage ?: "Server error ($code)", cause)

    /** Request timed out. */
    class Timeout(
        cause: Throwable? = null
    ) : ErrorEntity("Connection timed out", cause)

    /** HTTP error response (4xx, excluding specific cases). */
    class Http(
        val code: Int,
        httpMessage: String? = null,
        cause: Throwable? = null
    ) : ErrorEntity(httpMessage ?: "HTTP error ($code)", cause)

    /** An unexpected error occurred. */
    class Unknown(
        unknownMessage: String? = null,
        cause: Throwable? = null
    ) : ErrorEntity(unknownMessage ?: "An unexpected error occurred", cause)
}
