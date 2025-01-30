package com.spartapps.swipeablecards.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SwipeableCardsProperties(
    val visibleCardsInStack: Int = 3,
    val swipeThreshold: Dp = 5.dp,
    val lockBelowCardDragging: Boolean = true,
    val cardOffsetMultiplier: (Int, SwipeableCardsState) -> Offset = { index, state ->
        val offset = 30.dp.value * (index - state.currentCardIndex)
        Offset(-offset, offset)
    }
)
