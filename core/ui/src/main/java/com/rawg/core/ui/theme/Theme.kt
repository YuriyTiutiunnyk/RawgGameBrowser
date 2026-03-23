package com.rawg.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    surface = SurfaceDark,
    background = SurfaceDark
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

/**
 * Application theme wrapper — single entry point for the design system.
 *
 * Provides:
 * - Material 3 color scheme (light/dark)
 * - [Typography] scale
 * - [Spacing] tokens via [LocalSpacing] (`MaterialTheme.spacing`)
 * - [Dimens] tokens via [LocalDimens] (`MaterialTheme.dimens`)
 */
@Composable
fun RawgGamesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalDimens provides Dimens()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
