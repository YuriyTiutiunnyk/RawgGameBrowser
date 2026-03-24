package com.rawg.feature.games.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.rawg.core.presentation.components.ErrorScreen
import com.rawg.core.presentation.components.LoadingScreen
import com.rawg.core.ui.components.NetworkImage
import com.rawg.core.ui.components.RatingBar
import com.rawg.core.ui.theme.GradientOverlayEnd
import com.rawg.core.ui.theme.GradientOverlayStart
import com.rawg.core.ui.theme.MetacriticGreen
import com.rawg.core.ui.theme.MetacriticRed
import com.rawg.core.ui.theme.MetacriticYellow
import com.rawg.core.ui.theme.dimens
import com.rawg.core.ui.theme.spacing
import com.rawg.feature.games.domain.model.GameDetail
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Game Detail screen composable.
 *
 * Displays detailed information about a game including a hero image with gradient overlay,
 * name, rating, description, genres, platforms, developers, and publishers.
 *
 * Handles loading, error, and success states following the MVI pattern.
 *
 * @param gameId The unique game identifier.
 * @param onNavigateBack Callback to navigate back, provided by feature NavGraph.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(
    gameId: Int,
    onNavigateBack: () -> Unit,
    viewModel: GameDetailVm = koinViewModel { parametersOf(gameId) }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = state.gameDetail?.name ?: "Game Details",
                            maxLines = 1
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        ) { paddingValues ->
            when {
                state.isLoading -> {
                    LoadingScreen(modifier = Modifier.padding(paddingValues))
                }
                state.error != null -> {
                    ErrorScreen(
                        message = state.error,
                        onRetry = { viewModel.handleEvent(GameDetailEvent.OnRetry) },
                        modifier = Modifier.padding(paddingValues)
                    )
                }
                state.gameDetail != null -> {
                    GameDetailContent(
                        gameDetail = state.gameDetail,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GameDetailContent(
    gameDetail: GameDetail,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HeroImageSection(gameDetail)

        Column(modifier = Modifier.padding(spacing.default)) {
            GameMetadataSection(gameDetail)
            ChipSection(title = "Genres", items = gameDetail.genres)
            ChipSection(title = "Platforms", items = gameDetail.platforms)
            DescriptionSection(gameDetail.description)
            CreditsSection(gameDetail)
            Spacer(modifier = Modifier.height(spacing.xxLarge))
        }
    }
}

@Composable
private fun HeroImageSection(gameDetail: GameDetail) {
    val spacing = MaterialTheme.spacing
    val dimens = MaterialTheme.dimens

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimens.heroImageHeight)
    ) {
        NetworkImage(
            imageUrl = gameDetail.imageUrl,
            contentDescription = gameDetail.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(GradientOverlayStart, GradientOverlayEnd)
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(spacing.default)
        ) {
            Text(
                text = gameDetail.name,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(spacing.xxSmall))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RatingBar(rating = gameDetail.rating, starSize = dimens.iconMedium)
                if (gameDetail.metacritic != null) {
                    Spacer(modifier = Modifier.width(spacing.medium))
                    MetacriticBadge(score = gameDetail.metacritic)
                }
            }
        }
    }
}

@Composable
private fun GameMetadataSection(gameDetail: GameDetail) {
    val spacing = MaterialTheme.spacing

    gameDetail.released?.let { released ->
        InfoRow(label = "Released", value = released)
        Spacer(modifier = Modifier.height(spacing.small))
    }

    if (gameDetail.playtime > 0) {
        InfoRow(label = "Playtime", value = "${gameDetail.playtime} hours")
        Spacer(modifier = Modifier.height(spacing.small))
    }

    gameDetail.esrbRating?.let { esrb ->
        InfoRow(label = "ESRB Rating", value = esrb)
        Spacer(modifier = Modifier.height(spacing.small))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChipSection(title: String, items: List<String>) {
    if (items.isEmpty()) return

    val spacing = MaterialTheme.spacing

    Spacer(modifier = Modifier.height(spacing.small))
    SectionTitle(title)
    Spacer(modifier = Modifier.height(spacing.xxSmall))
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(spacing.small),
        verticalArrangement = Arrangement.spacedBy(spacing.xxSmall)
    ) {
        items.forEach { item ->
            AssistChip(
                onClick = {},
                label = { Text(item, style = MaterialTheme.typography.bodySmall) }
            )
        }
    }
}

@Composable
private fun DescriptionSection(description: String) {
    if (description.isBlank()) return

    val spacing = MaterialTheme.spacing

    Spacer(modifier = Modifier.height(spacing.default))
    SectionTitle("About")
    Spacer(modifier = Modifier.height(spacing.small))
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun CreditsSection(gameDetail: GameDetail) {
    val spacing = MaterialTheme.spacing

    if (gameDetail.developers.isNotEmpty()) {
        Spacer(modifier = Modifier.height(spacing.default))
        InfoRow(label = "Developers", value = gameDetail.developers.joinToString(", "))
    }

    if (gameDetail.publishers.isNotEmpty()) {
        Spacer(modifier = Modifier.height(spacing.small))
        InfoRow(label = "Publishers", value = gameDetail.publishers.joinToString(", "))
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MetacriticBadge(score: Int) {
    val dimens = MaterialTheme.dimens
    val spacing = MaterialTheme.spacing

    val color = when {
        score >= 75 -> MetacriticGreen
        score >= 50 -> MetacriticYellow
        else -> MetacriticRed
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(dimens.cornerSmall))
            .background(color.copy(alpha = 0.2f))
            .padding(horizontal = spacing.xSmall, vertical = spacing.xxxSmall)
    ) {
        Text(
            text = score.toString(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}
