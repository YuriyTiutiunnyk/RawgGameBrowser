package com.rawg.feature.favorites.di

import com.rawg.feature.favorites.presentation.FavoritesVm
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel { FavoritesVm(networkExecutor = get()) }
}
