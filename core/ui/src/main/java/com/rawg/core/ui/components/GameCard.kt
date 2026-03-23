package com.rawg.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import com.rawg.core.ui.theme.dimens
import com.rawg.core.ui.theme.spacing

/**
 * Game list item card displaying the game image, name, and rating.
 */
@Composable
fun GameCard(
    name: String,
    imageUrl: String?,
    rating: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing
    val dimens = MaterialTheme.dimens

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(dimens.cornerLarge),
        elevation = CardDefaults.cardElevation(defaultElevation = dimens.elevationDefault)
    ) {
        Column {
            NetworkImage(
                imageUrl = imageUrl,
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.gameCardImageHeight)
                    .clip(RoundedCornerShape(topStart = dimens.cornerLarge, topEnd = dimens.cornerLarge))
            )

            Column(modifier = Modifier.padding(spacing.medium)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(spacing.xxSmall))
                RatingBar(rating = rating)
            }
        }
    }
}
