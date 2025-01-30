package com.spartapps.swipeablecards.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class SwipeableCardsState(
    initialCardIndex: Int = 0,
    private val itemCount: () -> Int,
) {
    var currentCardIndex by mutableIntStateOf(initialCardIndex)
        private set

    var canSwipeBack by mutableStateOf(currentCardIndex > 0)
        private set

    fun goBack() {
        if (currentCardIndex > 0) {
            currentCardIndex--
            canSwipeBack = currentCardIndex > 0
        }
    }

    fun moveNext() {
        if (currentCardIndex < itemCount() - 1) {
            currentCardIndex++
            canSwipeBack = currentCardIndex > 0
        }
    }
}
