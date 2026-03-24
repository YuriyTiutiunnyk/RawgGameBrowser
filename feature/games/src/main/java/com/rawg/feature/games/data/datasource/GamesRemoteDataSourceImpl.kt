package com.rawg.feature.games.data.datasource

import com.rawg.core.network.helper.RetrofitHelper
import com.rawg.feature.games.data.api.GamesServiceApi
import com.rawg.feature.games.data.dto.GameDetailResponseDto
import com.rawg.feature.games.data.dto.GamesListResponseDto

/**
 * Implementation of [GamesRemoteDataSource] using Retrofit.
 *
 * Delegates API calls through [RetrofitHelper] for centralized error handling.
 * Delegates API calls through [RetrofitHelper] for centralized error handling.
 *
 * @param gamesServiceApi The Retrofit service interface for the RAWG API.
 * @param retrofitHelper Helper for executing API calls with error handling.
 */
class GamesRemoteDataSourceImpl(
    private val gamesServiceApi: GamesServiceApi,
    private val retrofitHelper: RetrofitHelper
) : GamesRemoteDataSource {

    override suspend fun getGames(page: Int, pageSize: Int): GamesListResponseDto {
        return retrofitHelper.apiExecute {
            gamesServiceApi.getGames(page = page, pageSize = pageSize)
        }
    }

    override suspend fun getGameDetail(id: Int): GameDetailResponseDto {
        return retrofitHelper.apiExecute {
            gamesServiceApi.getGameDetail(id = id)
        }
    }
}
