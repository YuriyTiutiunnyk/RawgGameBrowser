package com.rawg.games.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.rawg.feature.games.navigation.GamesGraphRoute
import com.rawg.feature.games.navigation.gamesNavGraph

/**
 * Host navigation graph — entry point for the entire app navigation.
 *
 * Uses type-safe navigation with @Serializable route objects.
 * Each feature registers its own screens via [NavGraphBuilder] extension.
 */
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = GamesGraphRoute
    ) {
        gamesNavGraph(navController)
    }
}
