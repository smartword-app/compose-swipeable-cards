package com.spartapps.swipeable.demo.cards_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spartapps.swipeable.demo.data.sampleData
import com.spartapps.swipeablecards.ui.SwipeableCards
import com.spartapps.swipeablecards.ui.rememberSwipeableCardsState

@Composable
fun CardsScreen(
    modifier: Modifier
) {
    val state = rememberSwipeableCardsState(
        itemCount = { sampleData.size }
    )

    Column(
        modifier = modifier,
    ) {
        SwipeableCards(
            items = sampleData,
            state = state,
            onSwipe = { _, _ ->

            }
        ) {
            CardItem(it)
        }
    }
}