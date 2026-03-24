package com.rawg.feature.games.presentation.detail

import com.rawg.core.presentation.extensions.NetworkExecutor
import com.rawg.core.presentation.vm.BaseVm
import com.rawg.feature.games.domain.interactor.GetGameDetailUseCase

class GameDetailVm(
    private val gameId: Int,
    private val getGameDetailUseCase: GetGameDetailUseCase,
    networkExecutor: NetworkExecutor
) : BaseVm<GameDetailState, GameDetailEvent>(GameDetailState(), networkExecutor) {

    init { loadGameDetail() }

    override fun handleEvent(event: GameDetailEvent) {
        when (event) {
            is GameDetailEvent.OnRetry -> loadGameDetail()
        }
    }

    private fun loadGameDetail() {
        networkExecutor {
            onStart { updateState { copy(isLoading = true, error = null) } }
            execute { getGameDetailUseCase(gameId) }
            success { detail ->
                updateState { copy(isLoading = false, gameDetail = detail, error = null) }
            }
            error { message ->
                updateState { copy(isLoading = false, error = message) }
            }
        }
    }
}
