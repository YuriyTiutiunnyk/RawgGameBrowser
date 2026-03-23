package com.rawg.core.domain.interactor

/**
 * Base use case interface for operations that require input and produce output.
 *
 * Uses Kotlin's operator overloading for clean call-site syntax.
 *
 * @param Input The parameter type required for execution (contravariant).
 * @param Output The result type returned from execution (covariant).
 *
 * Usage:
 * ```kotlin
 * class GetGameDetailUseCase(
 *     private val repository: GamesRepository
 * ) : UseCase<Int, GameDetail> {
 *     override suspend fun invoke(input: Int): GameDetail {
 *         return repository.getGameDetail(input)
 *     }
 * }
 *
 * // Call site:
 * val detail = getGameDetailUseCase(gameId)
 * ```
 */
interface UseCase<in Input, out Output> {
    suspend operator fun invoke(input: Input): Output
}
