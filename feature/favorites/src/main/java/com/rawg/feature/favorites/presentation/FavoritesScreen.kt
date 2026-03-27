package com.rawg.feature.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rawg.core.common.result.DataState
import com.rawg.core.presentation.components.ErrorScreen
import com.rawg.core.presentation.components.LoadingScreen
import com.rawg.core.presentation.navigation.NavigationEvent
import com.rawg.core.presentation.navigation.NavigationEventBus
import com.rawg.core.presentation.screen.BaseScreen
import com.rawg.core.ui.theme.dimens
import com.rawg.core.ui.theme.spacing
import com.rawg.feature.favorites.presentation.model.FavoriteGame
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun FavoritesScreen(
    navigationEventBus: NavigationEventBus = koinInject(),
    viewModel: FavoritesVm = koinViewModel()
) {
    BaseScreen(viewModel) { state, onEvent ->
        Scaffold(
            topBar = { FavoritesTopBar(onBackClick = { navigationEventBus.emit(NavigationEvent.Back) }) }
        ) { paddingValues ->
            FavoritesContent(
                state = state,
                onGameClick = { gameId -> navigationEventBus.emit(NavigationEvent.ToGameDetail(gameId)) },
                onRetry = { onEvent(FavoritesEvent.Refresh) },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesTopBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text("Favorites", style = MaterialTheme.typography.headlineMedium) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
private fun FavoritesContent(
    state: FavoritesState,
    onGameClick: (Int) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (state.favorites) {
        is DataState.Loading -> LoadingScreen(modifier = modifier)
        is DataState.Error -> ErrorScreen(
            message = state.favorites.message,
            onRetry = onRetry,
            modifier = modifier
        )
        is DataState.Empty -> FavoritesEmptyContent(modifier = modifier)
        is DataState.Success -> FavoritesList(
            games = state.favorites.data,
            onGameClick = onGameClick,
            modifier = modifier
        )
    }
}

@Composable
private fun FavoritesEmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("No favorites yet", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun FavoritesList(
    games: List<FavoriteGame>,
    onGameClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(spacing.default),
        verticalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        items(items = games, key = { it.id }) { game ->
            FavoriteItem(
                game = game,
                onClick = { onGameClick(game.id) }
            )
        }
    }
}

@Composable
private fun FavoriteItem(
    game: FavoriteGame,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing
    val dimens = MaterialTheme.dimens

    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = dimens.elevationDefault)
    ) {
        Row(
            modifier = Modifier.padding(spacing.default),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Column {
                Text(game.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Rating: ${"%.1f".format(game.rating)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
