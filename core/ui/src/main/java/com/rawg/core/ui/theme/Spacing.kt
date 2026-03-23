package com.rawg.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Design system spacing tokens.
 *
 * Single source of truth for all spacing/padding/margin values.
 * Access via [LocalSpacing] CompositionLocal or [MaterialTheme.spacing] extension.
 *
 * Usage:
 * ```kotlin
 * val spacing = MaterialTheme.spacing
 * Modifier.padding(spacing.medium)
 * ```
 */
data class Spacing(
    val xxxSmall: Dp = 2.dp,
    val xxSmall: Dp = 4.dp,
    val xSmall: Dp = 6.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 12.dp,
    val default: Dp = 16.dp,
    val large: Dp = 20.dp,
    val xLarge: Dp = 24.dp,
    val xxLarge: Dp = 32.dp,
    val xxxLarge: Dp = 48.dp,
    val huge: Dp = 64.dp
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }

/**
 * Extension property to access [Spacing] from [MaterialTheme].
 */
val androidx.compose.material3.MaterialTheme.spacing: Spacing
    @Composable
    get() = LocalSpacing.current
