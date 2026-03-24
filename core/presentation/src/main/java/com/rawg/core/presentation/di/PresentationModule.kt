package com.rawg.core.presentation.di

import com.rawg.core.presentation.extensions.NetworkExecutor
import org.koin.dsl.module

/**
 * Koin module for core presentation dependencies.
 *
 * Provides [NetworkExecutor] as a singleton — shared across all ViewModels
 * via [BaseVm]. The executor is a singleton shared across all ViewModels.
 */
val presentationModule = module {

    single {
        NetworkExecutor(
            exceptionHandler = get(),
            errorMapper = get()
        )
    }
}
