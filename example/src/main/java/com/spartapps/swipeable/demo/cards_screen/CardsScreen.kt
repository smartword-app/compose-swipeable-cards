package com.spartapps.swipeable.demo.cards_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spartapps.swipeable.demo.data.CardData
import com.spartapps.swipeablecards.state.rememberSwipeableCardsState
import com.spartapps.swipeablecards.ui.SwipeableCardDirection
import com.spartapps.swipeablecards.ui.lazy.LazySwipeableCards
import com.spartapps.swipeablecards.ui.lazy.items

/**
 * Reusable action button component with text label
 */
@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    contentDescription: String? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FloatingActionButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            textAlign = TextAlign.Center,
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun CardsScreen(
    data: List<CardData>,
    modifier: Modifier,
) {
    val state = rememberSwipeableCardsState(
        itemCount = { data.size }
    )

    Column(
        modifier = modifier,
    ) {
        LazySwipeableCards(
            modifier = Modifier.padding(20.dp),
            state = state,
            onSwipe = { item, direction ->
                Log.d("CardsScreen", "onSwipe: $item, $direction")
            },
        ) {
            items(data) { item, index, offset ->
                CardItem(
                    index = index,
                    cardData = item,
                    offset = offset
                )
            }
        }

        // Navigation controls row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ActionButton(
                onClick = { state.moveNext() },
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                text = "Next Card"
            )

            Text(
                text = "${state.currentCardIndex + 1} of ${data.size}",
            )

            ActionButton(
                onClick = { state.goBack() },
                icon = Icons.Outlined.Refresh,
                text = "Undo"
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        // Swipe controls row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ActionButton(
                onClick = { state.swipe(SwipeableCardDirection.Left) },
                icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                text = "Swipe\nLeft"
            )

            ActionButton(
                onClick = { state.swipe(SwipeableCardDirection.Right) },
                icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                text = "Swipe\nRight"
            )
        }
    }
}