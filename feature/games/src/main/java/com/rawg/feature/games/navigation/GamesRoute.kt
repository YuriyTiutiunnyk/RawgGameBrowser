package com.rawg.feature.games.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.rawg.feature.games.presentation.detail.GameDetailScreen
import com.rawg.feature.games.presentation.list.GamesListScreen
import kotlinx.serialization.Serializable

@Serializable
object GamesGraphRoute

@Serializable
object GamesListRoute

@Serializable
data class GameDetailRoute(val gameId: Int)

fun NavGraphBuilder.gamesNavGraph(navController: NavHostController) {
    navigation<GamesGraphRoute>(startDestination = GamesListRoute) {
        composable<GamesListRoute> {
            GamesListScreen(
                onNavigateToDetail = { gameId ->
                    navController.navigate(GameDetailRoute(gameId))
                }
            )
        }
        composable<GameDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<GameDetailRoute>()
            GameDetailScreen(
                gameId = route.gameId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
