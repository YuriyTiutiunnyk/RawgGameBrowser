package com.rawg.core.common.error

/**
 * Abstraction for converting typed [ErrorEntity] instances to user-friendly messages.
 *
 * Implementations live in the data/network layer (`core:network`), while this
 * interface lives in `core:common` — keeping the presentation layer decoupled
 * from network-specific details.
 */
interface ErrorMessageMapper {

    /**
     * Converts an [ErrorEntity] to a human-readable error message.
     */
    fun map(error: ErrorEntity): String
}
