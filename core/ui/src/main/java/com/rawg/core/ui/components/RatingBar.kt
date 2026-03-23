package com.rawg.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.rawg.core.ui.theme.RatingStar
import com.rawg.core.ui.theme.dimens

/**
 * Displays a rating value with a star icon.
 */
@Composable
fun RatingBar(
    rating: Double,
    starSize: Dp = MaterialTheme.dimens.iconSmall,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (rating > 0) Icons.Filled.Star else Icons.Outlined.Star,
            contentDescription = "Rating",
            modifier = Modifier.size(starSize),
            tint = RatingStar
        )
        Text(
            text = String.format("%.1f", rating),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
