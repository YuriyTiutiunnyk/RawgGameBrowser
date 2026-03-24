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

/**
 * Implementation of [GamesRepository] with Room-backed persistent cache.
 *
 * **Games list:** Network-first with Room fallback on error.
 * **Game details:** Room-first, network on cache miss.
 */
class GamesRepositoryImpl(
    private val database: GamesDatabase,
    private val remoteDataSource: GamesRemoteDataSource,
    private val gameDetailMapper: GameDetailMapper
) : GamesRepository {

    override suspend fun getGames(page: Int, pageSize: Int): GamesPage {
        return try {
            val response = remoteDataSource.getGames(page = page, pageSize = pageSize)
            val hasNextPage = response.next != null

            val baseIndex = (page - 1) * pageSize
            val entities = response.results.mapIndexed { index, dto ->
                dto.toEntity(page = page, orderIndex = baseIndex + index)
            }

            // Persist to Room
            if (page == STARTING_PAGE) {
                database.gamesDao().clearAll()
                database.pageInfoDao().clearAll()
            }
            database.gamesDao().insertAll(entities)
            database.pageInfoDao().insert(PageInfoEntity(page = page, hasNextPage = hasNextPage))

            GamesPage(
                games = entities.map { it.toDomain() },
                hasNextPage = hasNextPage
            )
        } catch (e: Exception) {
            // Fallback to Room cache
            val cached = database.gamesDao().getByPage(page)
            if (cached.isNotEmpty()) {
                val pageInfo = database.pageInfoDao().getByPage(page)
                GamesPage(
                    games = cached.map { it.toDomain() },
                    hasNextPage = pageInfo?.hasNextPage ?: false
                )
            } else {
                throw e
            }
        }
    }

    override suspend fun getGameDetail(id: Int): GameDetail {
        database.gameDetailDao().getById(id)?.let { cached ->
            return cached.toDomain()
        }

        val dto = remoteDataSource.getGameDetail(id)
        val entity = dto.toEntity()
        database.gameDetailDao().insert(entity)
        return gameDetailMapper.mapModel(dto)
    }

    companion object {
        private const val STARTING_PAGE = 1
    }
}
