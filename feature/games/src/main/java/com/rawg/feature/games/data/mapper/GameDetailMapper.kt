package com.rawg.feature.games.data.mapper

import com.rawg.core.common.mapper.Mapper
import com.rawg.feature.games.data.dto.GameDetailResponseDto
import com.rawg.feature.games.domain.model.GameDetail

/**
 * Maps [GameDetailResponseDto] (API response) to [GameDetail] (domain model).
 *
 * Transforms nested DTO structures (genres, platforms, developers, publishers)
 * into flat string lists for presentation layer consumption.
 */
class GameDetailMapper : Mapper<GameDetailResponseDto, GameDetail> {

    override fun mapModel(model: GameDetailResponseDto): GameDetail = GameDetail(
        id = model.id,
        name = model.name,
        imageUrl = model.backgroundImage,
        rating = model.rating,
        description = model.descriptionRaw.orEmpty(),
        released = model.released,
        metacritic = model.metacritic,
        genres = model.genres?.map { it.name }.orEmpty(),
        platforms = model.platforms?.map { it.platform.name }.orEmpty(),
        developers = model.developers?.map { it.name }.orEmpty(),
        publishers = model.publishers?.map { it.name }.orEmpty(),
        website = model.website,
        esrbRating = model.esrbRating?.name,
        ratingsCount = model.ratingsCount ?: 0,
        playtime = model.playtime ?: 0
    )
}
