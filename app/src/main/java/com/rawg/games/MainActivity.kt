package com.rawg.games

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rawg.core.ui.theme.RawgGamesTheme
import com.rawg.games.navigation.AppNavGraph

/**
 * Single Activity host for the application.
 *
 * Uses Jetpack Compose as the UI framework with edge-to-edge display.
 * All screen navigation is handled via Jetpack Navigation Compose.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RawgGamesTheme {
                AppNavGraph()
            }
        }
    }
}
