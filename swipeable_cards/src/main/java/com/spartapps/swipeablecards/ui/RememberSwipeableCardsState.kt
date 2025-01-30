package com.spartapps.swipeablecards.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberSwipeableCardsState(
    itemCount: () -> Int,
): SwipeableCardsState {
    val state = remember {
        SwipeableCardsState(
            itemCount = itemCount,
        )
    }
    return state
}