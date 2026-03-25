package com.rawg.core.domain.interactor

/**
 * Base use case interface with operator invoke for clean call-site syntax.
 *
 * @param Input The parameter type (contravariant).
 * @param Output The result type (covariant).
 */
interface UseCase<in Input, out Output> {
    suspend operator fun invoke(input: Input): Output
}
