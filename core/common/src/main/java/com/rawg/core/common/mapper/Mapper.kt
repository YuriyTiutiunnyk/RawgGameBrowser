package com.rawg.core.common.mapper

/**
 * Base interface for mapping between two model types.
 *
 * Provides a standardized way to transform data between layers (e.g., DTO → Domain Model).
 *
 * @param Input The source model type (contravariant — accepts subtypes)
 * @param Output The target model type (covariant — returns supertypes)
 *
 * Usage:
 * ```kotlin
 * class GameMapper : Mapper<GameDto, Game> {
 *     override fun mapModel(model: GameDto): Game = Game(
 *         id = model.id,
 *         name = model.name
 *     )
 * }
 * ```
 */
interface Mapper<in Input, out Output> {

    /**
     * Maps a single [Input] model to an [Output] model.
     */
    fun mapModel(model: Input): Output

    /**
     * Maps a list of [Input] models to a list of [Output] models.
     * Default implementation delegates to [mapModel] for each item.
     */
    fun mapToList(modelList: List<Input>): List<Output> {
        return modelList.map { mapModel(it) }
    }
}
