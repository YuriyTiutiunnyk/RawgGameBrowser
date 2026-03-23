package com.rawg.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Design system dimension tokens for component sizing.
 *
 * Single source of truth for heights, widths, icon sizes, corner radii, etc.
 * Access via [LocalDimens] CompositionLocal or [MaterialTheme.dimens] extension.
 */
data class Dimens(
    // Corner radius
    val cornerSmall: Dp = 4.dp,
    val cornerMedium: Dp = 8.dp,
    val cornerLarge: Dp = 12.dp,
    val cornerXLarge: Dp = 16.dp,

    // Icon sizes
    val iconSmall: Dp = 16.dp,
    val iconMedium: Dp = 20.dp,
    val iconLarge: Dp = 24.dp,
    val iconXLarge: Dp = 64.dp,

    // Image heights
    val gameCardImageHeight: Dp = 180.dp,
    val heroImageHeight: Dp = 280.dp,

    // Misc
    val progressStrokeSmall: Dp = 2.dp,
    val dividerHeight: Dp = 1.dp,
    val elevationDefault: Dp = 4.dp
)

val LocalDimens = staticCompositionLocalOf { Dimens() }

/**
 * Extension property to access [Dimens] from [MaterialTheme].
 */
val androidx.compose.material3.MaterialTheme.dimens: Dimens
    @Composable
    get() = LocalDimens.current
