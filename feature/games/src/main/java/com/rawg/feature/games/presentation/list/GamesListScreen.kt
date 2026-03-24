package com.rawg.feature.games.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import com.rawg.core.common.result.DataState
import com.rawg.core.presentation.components.ErrorScreen
import com.rawg.core.presentation.components.LoadingScreen
import com.rawg.core.presentation.screen.BaseScreen
import com.rawg.core.ui.components.GameCard
import com.rawg.core.ui.theme.spacing
import com.rawg.feature.games.domain.model.Game
import org.koin.androidx.compose.koinViewModel

private const val LOAD_MORE_THRESHOLD = 5

/**
 * Games List screen — pure rendering from UiState.
 *
 * Navigation via callbacks from feature NavGraph.
 * Pagination via [GamesListEvent.LoadMore] triggered on scroll.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesListScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: GamesListVm = koinViewModel()
) {
    BaseScreen(viewModel) { state, onEvent ->
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { GamesTopBar(scrollBehavior) }
        ) { paddingValues ->
            GamesListContent(
                state = state,
                onGameClicked = onNavigateToDetail,
                onEvent = onEvent,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

//  Top Bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GamesTopBar(scrollBehavior: androidx.compose.material3.TopAppBarScrollBehavior) {
    TopAppBar(
        title = {
            Text(
                text = "RAWG Games",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        scrollBehavior = scrollBehavior
    )
}

//  Content State Router
@Composable
private fun GamesListContent(
    state: GamesListState,
    onGameClicked: (Int) -> Unit,
    onEvent: (GamesListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state.games) {
        is DataState.Loading -> LoadingScreen(modifier = modifier)

        is DataState.Error -> ErrorScreen(
            message = state.games.message,
            onRetry = { onEvent(GamesListEvent.Retry) },
            modifier = modifier
        )

        is DataState.Empty -> EmptyContent(modifier = modifier)

        is DataState.Success -> GamesList(
            games = state.games.data,
            isLoadingMore = state.isLoadingMore,
            loadMoreError = state.loadMoreError,
            hasMoreData = state.hasMoreData,
            onGameClicked = onGameClicked,
            onLoadMore = { onEvent(GamesListEvent.LoadMore) },
            onRetryLoadMore = { onEvent(GamesListEvent.RetryLoadMore) },
            modifier = modifier
        )
    }
}

//  Empty State
@Composable
private fun EmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No games found",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

//  Games List (Success State)
@Composable
private fun GamesList(
    games: List<Game>,
    isLoadingMore: Boolean,
    loadMoreError: String?,
    hasMoreData: Boolean,
    onGameClicked: (Int) -> Unit,
    onLoadMore: () -> Unit,
    onRetryLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing
    val listState = rememberLazyListState()

    InfiniteScrollEffect(listState, hasMoreData && loadMoreError == null, isLoadingMore, onLoadMore)

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(spacing.default),
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        items(items = games, key = { it.id }) { game ->
            GameCard(
                name = game.name,
                imageUrl = game.imageUrl,
                rating = game.rating,
                onClick = { onGameClicked(game.id) }
            )
        }

        if (isLoadingMore) {
            item { AppendLoadingItem() }
        }

        if (loadMoreError != null) {
            item { AppendErrorItem(message = loadMoreError, onRetry = onRetryLoadMore) }
        }
    }
}

//  Infinite Scroll Detection
@Composable
private fun InfiniteScrollEffect(
    listState: LazyListState,
    hasMoreData: Boolean,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = listState.layoutInfo.totalItemsCount
            hasMoreData && !isLoadingMore && lastVisible >= totalItems - LOAD_MORE_THRESHOLD
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) onLoadMore()
    }
}

//  Append States
@Composable
private fun AppendLoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.default),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun AppendErrorItem(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.default),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
