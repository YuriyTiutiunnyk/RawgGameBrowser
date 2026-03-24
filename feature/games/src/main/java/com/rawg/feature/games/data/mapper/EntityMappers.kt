package com.rawg.feature.games.data.mapper

import com.rawg.feature.games.data.dto.GameItemDto
import com.rawg.feature.games.data.dto.GameDetailResponseDto
import com.rawg.feature.games.data.local.GameEntity
import com.rawg.feature.games.data.local.GameDetailEntity
import com.rawg.feature.games.domain.model.Game
import com.rawg.feature.games.domain.model.GameDetail

/**
 * Maps between Room entities and domain models, and from DTOs to entities.
 */

// ── DTO → Entity ──

fun GameItemDto.toEntity(page: Int, orderIndex: Int): GameEntity = GameEntity(
    id = id,
    name = name,
    imageUrl = backgroundImage,
    rating = rating,
    page = page,
    orderIndex = orderIndex
)

fun GameDetailResponseDto.toEntity(): GameDetailEntity = GameDetailEntity(
    id = id,
    name = name,
    imageUrl = backgroundImage,
    rating = rating,
    description = descriptionRaw.orEmpty(),
    released = released,
    metacritic = metacritic,
    genres = genres?.map { it.name }.orEmpty(),
    platforms = platforms?.map { it.platform.name }.orEmpty(),
    developers = developers?.map { it.name }.orEmpty(),
    publishers = publishers?.map { it.name }.orEmpty(),
    website = website,
    esrbRating = esrbRating?.name,
    ratingsCount = ratingsCount ?: 0,
    playtime = playtime ?: 0
)

// ── Entity → Domain ──

fun GameEntity.toDomain(): Game = Game(
    id = id,
    name = name,
    imageUrl = imageUrl,
    rating = rating
)

fun GameDetailEntity.toDomain(): GameDetail = GameDetail(
    id = id,
    name = name,
    imageUrl = imageUrl,
    rating = rating,
    description = description,
    released = released,
    metacritic = metacritic,
    genres = genres,
    platforms = platforms,
    developers = developers,
    publishers = publishers,
    website = website,
    esrbRating = esrbRating,
    ratingsCount = ratingsCount,
    playtime = playtime
)
