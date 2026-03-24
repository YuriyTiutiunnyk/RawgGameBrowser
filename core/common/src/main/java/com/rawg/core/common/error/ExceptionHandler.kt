package com.rawg.core.common.error

/**
 * Abstraction for classifying raw throwables into typed [ErrorEntity] instances.
 *
 * Implementations live in the data/network layer (`core:network`), while this
 * interface lives in `core:common` — allowing `core:presentation` to depend
 * on the abstraction without knowing about network details.
 */
interface ExceptionHandler {

    /**
     * Converts a raw [Throwable] to a typed [ErrorEntity].
     *
     * @param throwable The original exception.
     * @return A categorized [ErrorEntity] for presentation layer consumption.
     */
    fun handle(throwable: Throwable): ErrorEntity
}
