package com.rawg.feature.games.presentation.list

import com.rawg.core.common.result.DataState
import com.rawg.core.presentation.extensions.NetworkExecutor
import com.rawg.core.presentation.vm.BaseVm
import com.rawg.feature.games.domain.interactor.GetGamesParams
import com.rawg.feature.games.domain.interactor.GetGamesUseCase

/**
 * ViewModel for the Games List screen.
 *
 * Manual pagination — accumulates items in [GamesListState.games].
 * Single source of truth via UiState (no Paging 3 dependency).
 */
class GamesListVm(
    private val getGamesUseCase: GetGamesUseCase,
    networkExecutor: NetworkExecutor
) : BaseVm<GamesListState, GamesListEvent>(GamesListState(), networkExecutor) {

    private var currentPage = 1

    init {
        loadGames()
    }

    override fun handleEvent(event: GamesListEvent) {
        when (event) {
            is GamesListEvent.LoadMore,
            is GamesListEvent.RetryLoadMore -> loadMore()

            is GamesListEvent.Retry -> loadGames()
        }
    }

    private fun loadGames() {
        currentPage = 1
        networkExecutor {
            onStart { updateState { copy(games = DataState.Loading) } }
            execute { getGamesUseCase(GetGamesParams(page = currentPage)) }
            success { page ->
                updateState {
                    copy(
                        games = if (page.games.isEmpty()) DataState.Empty
                        else DataState.Success(page.games),
                        hasMoreData = page.hasNextPage
                    )
                }
            }
            error { message ->
                updateState { copy(games = DataState.Error(message)) }
            }
        }
    }

    private fun loadMore() {
        val currentState = getState()
        if (currentState.isLoadingMore || !currentState.hasMoreData) return
        val currentGames = (currentState.games as? DataState.Success)?.data ?: return

        currentPage++
        networkExecutor {
            onStart { updateState { copy(isLoadingMore = true, loadMoreError = null) } }
            execute { getGamesUseCase(GetGamesParams(page = currentPage)) }
            success { page ->
                updateState {
                    copy(
                        games = DataState.Success(currentGames + page.games),
                        hasMoreData = page.hasNextPage,
                        isLoadingMore = false
                    )
                }
            }
            error { message ->
                currentPage--
                updateState { copy(isLoadingMore = false, loadMoreError = message) }
            }
        }
    }
}
