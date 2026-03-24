package com.rawg.games

import android.app.Application
import com.rawg.core.network.di.networkModule
import com.rawg.core.presentation.di.presentationModule
import com.rawg.feature.games.di.gamesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Application class that initializes the Koin dependency injection framework.
 *
 * Initializes Koin with staged module loading for all app dependencies.
 * Loads network module (Retrofit, OkHttp, error handling) and feature modules.
 */
class RawgApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@RawgApplication)
            modules(
                networkModule(
                    apiKey = BuildConfig.RAWG_API_KEY,
                    isDebug = BuildConfig.DEBUG
                ),
                presentationModule,
                gamesModule
            )
        }
    }
}
