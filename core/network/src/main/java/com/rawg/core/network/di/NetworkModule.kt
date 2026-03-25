package com.rawg.core.network.di

import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import com.rawg.core.common.error.ErrorMessageMapper
import com.rawg.core.common.error.ExceptionHandler
import com.rawg.core.network.error.ErrorMapper
import com.rawg.core.network.error.NetworkExceptionHandler
import com.rawg.core.network.helper.RetrofitHelper
import com.rawg.core.network.helper.RetrofitHelperImpl
import com.rawg.core.network.interceptor.ApiKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.rawg.io/api/"
private const val TIMEOUT_SECONDS = 30L

/**
 * Koin module providing all networking dependencies.
 *
 * Configures Retrofit, OkHttp, Gson, interceptors, and error handling utilities.
 * Provides [ExceptionHandler] and [ErrorMessageMapper] abstractions that
 * `core:presentation` depends on without knowing about `core:network`.
 *
 * @param apiKey The RAWG API key to be injected into all requests.
 * @param isDebug Whether to enable HTTP logging (should be true only in debug builds).
 */
fun networkModule(apiKey: String, isDebug: Boolean) = module {

    single {
        GsonBuilder()
            .setStrictness(Strictness.LENIENT)
            .create()
    }

    single {
        OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)

            addInterceptor(ApiKeyInterceptor(apiKey))

            if (isDebug) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single<ExceptionHandler> { NetworkExceptionHandler() }

    single<ErrorMessageMapper> { ErrorMapper() }

    single<RetrofitHelper> {
        RetrofitHelperImpl(exceptionHandler = get())
    }
}
