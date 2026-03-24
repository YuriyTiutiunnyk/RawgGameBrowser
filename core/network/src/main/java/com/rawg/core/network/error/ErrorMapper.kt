package com.rawg.core.network.error

import com.rawg.core.common.error.ErrorEntity
import com.rawg.core.common.error.ErrorMessageMapper

/**
 * Maps [ErrorEntity] instances to user-friendly display messages.
 *
 * Implements [ErrorMessageMapper] from `core:common` so that `core:presentation`
 * can depend on the abstraction without knowing about this implementation.
 */
class ErrorMapper : ErrorMessageMapper {

    /**
     * Converts an [ErrorEntity] to a human-readable error message.
     */
    override fun map(error: ErrorEntity): String {
        return when (error) {
            is ErrorEntity.NoConnection ->
                "No internet connection. Please check your network and try again."

            is ErrorEntity.Timeout ->
                "Connection timed out. Please try again."

            is ErrorEntity.Server ->
                "Something went wrong on our end. Please try again later."

            is ErrorEntity.Http ->
                "Unable to load data. Please try again."

            is ErrorEntity.Unknown ->
                "An unexpected error occurred. Please try again."
        }
    }
}
