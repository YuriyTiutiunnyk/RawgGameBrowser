package com.rawg.core.network.error

import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

/**
 * Maps throwables to typed [NetworkErrorEntity] instances.
 *
 * Provides centralized error classification.
 */
class NetworkExceptionHandler {

    /**
     * Converts a raw [Throwable] to a typed [NetworkErrorEntity].
     *
     * @param throwable The original exception from the network layer.
     * @return A categorized [NetworkErrorEntity] for presentation layer consumption.
     */
    fun handle(throwable: Throwable): NetworkErrorEntity {
        return when (throwable) {
            is NetworkErrorEntity -> throwable

            is SocketTimeoutException -> NetworkErrorEntity.TimeoutError(throwable)

            is UnknownHostException,
            is ConnectException -> NetworkErrorEntity.NoConnectionError(throwable)

            is IOException -> NetworkErrorEntity.NoConnectionError(throwable)

            is HttpException -> {
                val code = throwable.code()
                when {
                    code in 500..599 -> NetworkErrorEntity.ServerError(
                        code = code,
                        serverMessage = throwable.message(),
                        cause = throwable
                    )
                    else -> NetworkErrorEntity.HttpError(
                        code = code,
                        httpMessage = throwable.message(),
                        cause = throwable
                    )
                }
            }

            else -> NetworkErrorEntity.UnknownError(
                unknownMessage = throwable.message,
                cause = throwable
            )
        }
    }
}
