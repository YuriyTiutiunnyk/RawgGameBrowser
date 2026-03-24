package com.rawg.core.network.error

import com.rawg.core.common.error.ErrorEntity
import com.rawg.core.common.error.ExceptionHandler
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

/**
 * Maps throwables to typed [ErrorEntity] instances.
 *
 * Provides centralized error classification for the network layer.
 * Implements [ExceptionHandler] from `core:common` so that `core:presentation`
 * can depend on the abstraction without knowing about this implementation.
 */
class NetworkExceptionHandler : ExceptionHandler {

    /**
     * Converts a raw [Throwable] to a typed [ErrorEntity].
     *
     * @param throwable The original exception from the network layer.
     * @return A categorized [ErrorEntity] for presentation layer consumption.
     */
    override fun handle(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is ErrorEntity -> throwable

            is SocketTimeoutException -> ErrorEntity.Timeout(throwable)

            is UnknownHostException,
            is ConnectException -> ErrorEntity.NoConnection(throwable)

            is IOException -> ErrorEntity.NoConnection(throwable)

            is HttpException -> {
                val code = throwable.code()
                when {
                    code in 500..599 -> ErrorEntity.Server(
                        code = code,
                        serverMessage = throwable.message(),
                        cause = throwable
                    )
                    else -> ErrorEntity.Http(
                        code = code,
                        httpMessage = throwable.message(),
                        cause = throwable
                    )
                }
            }

            else -> ErrorEntity.Unknown(
                unknownMessage = throwable.message,
                cause = throwable
            )
        }
    }
}
