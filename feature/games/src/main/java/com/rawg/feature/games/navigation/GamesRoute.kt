package com.rawg.feature.games.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rawg.feature.games.presentation.list.GamesListScreen

object GamesRoute {
    const val ROOT = "games"
    const val LIST = "games_list"
}

fun NavGraphBuilder.gamesNavGraph(navController: NavHostController) {
    navigation(startDestination = GamesRoute.LIST, route = GamesRoute.ROOT) {
        composable(route = GamesRoute.LIST) {
            GamesListScreen()
        }
    }
}
