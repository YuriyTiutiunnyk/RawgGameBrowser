package com.rawg.feature.games.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rawg.core.common.result.DataState
import com.rawg.core.presentation.components.ErrorScreen
import com.rawg.core.presentation.components.LoadingScreen
import com.rawg.core.ui.components.GameCard
import com.rawg.core.ui.theme.spacing
import com.rawg.feature.games.domain.model.Game
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesListScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: GamesListVm = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("RAWG Games", style = MaterialTheme.typography.headlineMedium) },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        when (state.games) {
            is DataState.Loading -> LoadingScreen(modifier = Modifier.padding(paddingValues))
            is DataState.Error -> ErrorScreen(
                message = (state.games as DataState.Error).message,
                onRetry = { viewModel.handleEvent(GamesListEvent.Retry) },
                modifier = Modifier.padding(paddingValues)
            )
            is DataState.Empty -> Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) { Text("No games found", style = MaterialTheme.typography.bodyLarge) }
            is DataState.Success -> {
                val spacing = MaterialTheme.spacing
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(spacing.default),
                    verticalArrangement = Arrangement.spacedBy(spacing.medium)
                ) {
                    items(
                        items = (state.games as DataState.Success<List<Game>>).data,
                        key = { it.id }
                    ) { game ->
                        GameCard(
                            name = game.name,
                            imageUrl = game.imageUrl,
                            rating = game.rating,
                            onClick = { onNavigateToDetail(game.id) }
                        )
                    }
                }
            }
        }
    }
}
