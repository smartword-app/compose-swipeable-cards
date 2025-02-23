package com.spartapps.swipeablecards.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

/**
 * Manages the state of a SwipeableCards stack.
 *
 * This class maintains the current position in the card stack and handles navigation
 * between cards while enforcing boundaries and tracking navigation possibilities.
 *
 * @property initialCardIndex Starting position in the card stack. Defaults to 0.
 * @property itemCount Lambda that returns the total number of items in the stack.
 *                    Provided as a lambda to support dynamic lists.
 *
 * @property currentCardIndex The index of the currently displayed top card.
 *                           Read-only from outside the class, modified through navigation methods.
 * @property canSwipeBack Indicates whether backwards navigation is possible (true if not at first card).
 */
class SwipeableCardsState(
    initialCardIndex: Int = 0,
    private val itemCount: () -> Int,
) {

    var size by mutableStateOf(IntSize.Zero)
        private set

    var dragOffset by mutableStateOf(Offset.Zero)
        private set

    var currentCardIndex by mutableIntStateOf(initialCardIndex)
        private set

    var canSwipeBack by mutableStateOf(currentCardIndex > 0)
        private set

    fun onDragOffsetChange(offset: Offset) {
        dragOffset = offset
    }

    fun onSizeChange(size: IntSize) {
        this.size = size
    }

    fun goBack() {
        if (currentCardIndex > 0) {
            currentCardIndex--
            canSwipeBack = currentCardIndex > 0
        }
    }

    fun moveNext() {
        if (currentCardIndex < itemCount()) {
            currentCardIndex++
            canSwipeBack = currentCardIndex > 0
        }
    }
}
