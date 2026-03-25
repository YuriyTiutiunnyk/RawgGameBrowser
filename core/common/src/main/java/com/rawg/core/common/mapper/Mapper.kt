package com.rawg.core.common.mapper

/**
 * Base interface for mapping between two model types (e.g., DTO → Domain).
 *
 * @param Input The source model type (contravariant).
 * @param Output The target model type (covariant).
 */
interface Mapper<in Input, out Output> {

    fun mapModel(model: Input): Output

    fun mapToList(modelList: List<Input>): List<Output> {
        return modelList.map { mapModel(it) }
    }
}
