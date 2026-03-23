package com.rawg.core.common.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Provides coroutine dispatchers for different execution contexts.
 *
 * Abstraction over [Dispatchers] to enable testing with [TestDispatcherProvider].
 * All coroutine-dependent classes should inject this interface rather than
 * using [Dispatchers] directly.
 */
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val immediate: CoroutineDispatcher
}

/**
 * Default production implementation of [DispatcherProvider].
 */
class DefaultDispatcherProvider : DispatcherProvider {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val default: CoroutineDispatcher = Dispatchers.Default
    override val immediate: CoroutineDispatcher = Dispatchers.Main.immediate
}
