package com.rawg.games.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.rawg.feature.games.navigation.GamesRoute
import com.rawg.feature.games.navigation.gamesNavGraph

/**
 * Host navigation graph — entry point for the entire app navigation.
 *
 * Holds feature graphs as nested navigation nodes.
 * Each feature registers its own screens via extension function on [NavGraphBuilder].

 *
 * **Intra-feature** navigation (between screens within feature) is handled
 * inside each feature's NavGraph extension.
 *
 * **Inter-feature** navigation (between feature graphs) would use
 * `navController.navigate(FeatureRoute.ROOT)` from any feature.
 */
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = GamesRoute.ROOT
    ) {
        // Current feature graph
        gamesNavGraph(navController)
        // Future features, for example:
        // settingsNavGraph(navController)
    }
}
