package com.rawg.core.network.error

/**
 * Maps [NetworkErrorEntity] instances to user-friendly display messages.
 *
 * Provides a centralized location for error message localization and customization.
 */
class ErrorMapper {

    /**
     * Converts a [NetworkErrorEntity] to a human-readable error message.
     */
    fun map(error: NetworkErrorEntity): String {
        return when (error) {
            is NetworkErrorEntity.NoConnectionError ->
                "No internet connection. Please check your network and try again."

            is NetworkErrorEntity.TimeoutError ->
                "Connection timed out. Please try again."

            is NetworkErrorEntity.ServerError ->
                "Something went wrong on our end. Please try again later."

            is NetworkErrorEntity.HttpError ->
                "Unable to load data. Please try again."

            is NetworkErrorEntity.UnknownError ->
                "An unexpected error occurred. Please try again."
        }
    }
}
