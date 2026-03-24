package com.rawg.feature.games.presentation.list

import com.rawg.core.common.result.DataState
import com.rawg.core.presentation.extensions.networkExecutor
import com.rawg.core.presentation.vm.BaseVm
import com.rawg.feature.games.domain.interactor.GetGamesUseCase

class GamesListVm(
    private val getGamesUseCase: GetGamesUseCase
) : BaseVm<GamesListState, GamesListEvent>(GamesListState()) {

    init {
        loadGames()
    }

    override fun handleEvent(event: GamesListEvent) {
        when (event) {
            is GamesListEvent.Retry -> loadGames()
        }
    }

    private fun loadGames() {
        networkExecutor {
            onStart { updateState { copy(games = DataState.Loading) } }
            execute { getGamesUseCase(page = 1) }
            success { page ->
                updateState {
                    copy(
                        games = if (page.games.isEmpty()) DataState.Empty
                        else DataState.Success(page.games)
                    )
                }
            }
            error { message ->
                updateState { copy(games = DataState.Error(message)) }
            }
        }
    }
}
