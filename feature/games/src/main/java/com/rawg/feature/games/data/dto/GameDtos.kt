package com.rawg.feature.games.data.dto

import com.google.gson.annotations.SerializedName
import com.rawg.core.common.annotation.DTO

/**
 * DTO for the paginated games list API response.
 *
 * Maps directly to the JSON response from `GET /api/games`.
 */
@DTO
data class GamesListResponseDto(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<GameItemDto>
)

/**
 * DTO for a single game item in the list response.
 */
@DTO
data class GameItemDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("background_image") val backgroundImage: String?,
    @SerializedName("rating") val rating: Double
)

/**
 * DTO for game detail API response.
 *
 * Maps directly to the JSON response from `GET /api/games/{id}`.
 */
@DTO
data class GameDetailResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("background_image") val backgroundImage: String?,
    @SerializedName("rating") val rating: Double,
    @SerializedName("description_raw") val descriptionRaw: String?,
    @SerializedName("released") val released: String?,
    @SerializedName("metacritic") val metacritic: Int?,
    @SerializedName("genres") val genres: List<GenreDto>?,
    @SerializedName("platforms") val platforms: List<PlatformWrapperDto>?,
    @SerializedName("developers") val developers: List<DeveloperDto>?,
    @SerializedName("publishers") val publishers: List<PublisherDto>?,
    @SerializedName("esrb_rating") val esrbRating: EsrbRatingDto?,
    @SerializedName("playtime") val playtime: Int?
)

@DTO
data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

@DTO
data class PlatformWrapperDto(
    @SerializedName("platform") val platform: PlatformDto
)

@DTO
data class PlatformDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

@DTO
data class DeveloperDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

@DTO
data class PublisherDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

@DTO
data class EsrbRatingDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
