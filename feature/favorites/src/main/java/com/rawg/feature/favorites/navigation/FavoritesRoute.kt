package com.rawg.feature.favorites.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rawg.feature.favorites.presentation.FavoritesScreen
import kotlinx.serialization.Serializable

@Serializable
object FavoritesGraphRoute

@Serializable
object FavoritesListRoute

fun NavGraphBuilder.favoritesNavGraph() {
    navigation<FavoritesGraphRoute>(startDestination = FavoritesListRoute) {
        composable<FavoritesListRoute> {
            FavoritesScreen()
        }
    }
}
