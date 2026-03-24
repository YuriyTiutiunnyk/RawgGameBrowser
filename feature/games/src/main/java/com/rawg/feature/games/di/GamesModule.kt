package com.rawg.feature.games.di

import androidx.room.Room
import com.rawg.feature.games.data.api.GamesServiceApi
import com.rawg.feature.games.data.datasource.GamesRemoteDataSource
import com.rawg.feature.games.data.datasource.GamesRemoteDataSourceImpl
import com.rawg.feature.games.data.local.GamesDatabase
import com.rawg.feature.games.data.mapper.GameDetailMapper
import com.rawg.feature.games.data.repository.GamesRepositoryImpl
import com.rawg.feature.games.domain.interactor.GetGameDetailUseCase
import com.rawg.feature.games.domain.interactor.GetGamesUseCase
import com.rawg.feature.games.domain.repository.GamesRepository
import com.rawg.feature.games.presentation.detail.GameDetailVm
import com.rawg.feature.games.presentation.list.GamesListVm
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val gamesModule = module {
    single<GamesServiceApi> { get<Retrofit>().create(GamesServiceApi::class.java) }
    single {
        Room.databaseBuilder(androidContext(), GamesDatabase::class.java, "rawg_games_db").build()
    }
    single { GameDetailMapper() }
    single<GamesRemoteDataSource> {
        GamesRemoteDataSourceImpl(gamesServiceApi = get(), retrofitHelper = get())
    }
    single<GamesRepository> {
        GamesRepositoryImpl(database = get(), remoteDataSource = get(), gameDetailMapper = get())
    }
    factory { GetGamesUseCase(repository = get()) }
    factory { GetGameDetailUseCase(repository = get()) }
    viewModel { GamesListVm(getGamesUseCase = get()) }
    viewModel { params -> GameDetailVm(gameId = params[0], getGameDetailUseCase = get()) }
}
