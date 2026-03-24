package com.rawg.feature.games.data.repository

import com.rawg.feature.games.data.datasource.GamesRemoteDataSource
import com.rawg.feature.games.data.local.GamesDatabase
import com.rawg.feature.games.data.local.PageInfoEntity
import com.rawg.feature.games.data.mapper.GameDetailMapper
import com.rawg.feature.games.data.mapper.toDomain
import com.rawg.feature.games.data.mapper.toEntity
import com.rawg.feature.games.domain.model.GameDetail
import com.rawg.feature.games.domain.model.GamesPage
import com.rawg.feature.games.domain.repository.GamesRepository

class GamesRepositoryImpl(
    private val database: GamesDatabase,
    private val remoteDataSource: GamesRemoteDataSource,
    private val gameDetailMapper: GameDetailMapper
) : GamesRepository {

    override suspend fun getGames(page: Int, pageSize: Int): GamesPage {
        val response = remoteDataSource.getGames(page = page, pageSize = pageSize)
        val hasNextPage = response.next != null
        val baseIndex = (page - 1) * pageSize
        val entities = response.results.mapIndexed { index, dto ->
            dto.toEntity(page = page, orderIndex = baseIndex + index)
        }
        if (page == 1) {
            database.gamesDao().clearAll()
            database.pageInfoDao().clearAll()
        }
        database.gamesDao().insertAll(entities)
        database.pageInfoDao().insert(PageInfoEntity(page = page, hasNextPage = hasNextPage))
        return GamesPage(games = entities.map { it.toDomain() }, hasNextPage = hasNextPage)
    }

    override suspend fun getGameDetail(id: Int): GameDetail {
        val dto = remoteDataSource.getGameDetail(id)
        val entity = dto.toEntity()
        database.gameDetailDao().insert(entity)
        return gameDetailMapper.mapModel(dto)
    }
}
