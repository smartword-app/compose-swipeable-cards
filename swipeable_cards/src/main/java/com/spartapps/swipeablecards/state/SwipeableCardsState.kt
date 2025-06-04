package com.spartapps.swipeablecards.state

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import com.spartapps.swipeablecards.ui.SwipeableCardDirection
import com.spartapps.swipeablecards.ui.SwipeableCardsDefaults

/**
 * Manages the state of a SwipeableCards stack.
 *
 * This class maintains the current position in the card stack and handles navigation
 * between cards while enforcing boundaries and tracking navigation possibilities.
 */
class SwipeableCardsState(
    val visibleCardsInStack: Int = SwipeableCardsDefaults.VISIBLE_CARDS_IN_STACK,
    initialCardIndex: Int = 0,
    private val itemCount: () -> Int,
) {

    /**
     * The size of the container holding the swipeable cards.
     * Used for calculating proper animation boundaries and card positioning.
     */
    var size by mutableStateOf(IntSize.Zero)
        private set

    /**
     * Stores the current drag offsets for each card by index.
     * Used to track and animate multiple cards independently.
     */
    val dragOffsets = mutableStateMapOf<Int, Offset>()

    /**
     * The index of the currently displayed top card.
     * Read-only from outside the class, modified through navigation methods.
     */
    var currentCardIndex by mutableIntStateOf(initialCardIndex)
        private set

    /**
     * Tracks cards that are currently in a swiping animation.
     * Used to maintain proper rendering order during transitions.
     */
    val swipingVisibleCards = mutableStateListOf<Int>()

    /**
     * Indicates whether backwards navigation is possible (true if not at first card).
     */
    var canSwipeBack = derivedStateOf { currentCardIndex > 0 }
        private set

    val visibleCardIndexes = derivedStateOf {
        val maxVisible = currentCardIndex + visibleCardsInStack - 1
        val lastIndex = minOf(maxVisible, itemCount() - 1)
        (currentCardIndex..lastIndex).toList() + swipingVisibleCards
    }

    internal fun onDragOffsetChange(
        index: Int,
        offset: Offset,
    ) {
        dragOffsets[index] = offset
    }

    internal fun onSizeChange(size: IntSize) {
        this.size = size
    }

    /**
     * Goes back to the previous card in the stack.
     * Has no effect if already at the first card.
     * Updates [canSwipeBack] based on the new position.
     */
    fun goBack() {
        swipingVisibleCards.remove(currentCardIndex)
        if (currentCardIndex > 0) {
            currentCardIndex--
            dragOffsets.remove(currentCardIndex)
            swipingVisibleCards.remove(currentCardIndex)
        }
    }

    /**
     * Moves to the next card in the stack.
     * Has no effect if already at the last card.
     * Updates [canSwipeBack] based on the new position.
     */
    fun moveNext() {
        swipingVisibleCards.remove(currentCardIndex - 1)
        if (currentCardIndex < itemCount()) {
            currentCardIndex++
        }
    }

    /**
     * Programmatically swipes the current top card in the specified direction.
     * This will animate the card off-screen and advance to the next card.
     *
     * @param direction The direction to swipe the card ([SwipeableCardDirection.Left] or [SwipeableCardDirection.Right]).
     */
    fun swipe(direction: SwipeableCardDirection) {
        val targetX = when (direction) {
            SwipeableCardDirection.Left -> -size.width.toFloat() * 1.5f
            SwipeableCardDirection.Right -> size.width.toFloat() * 1.5f
        }

        swipingVisibleCards.add(currentCardIndex)
        dragOffsets[currentCardIndex] = Offset(targetX, 0f)
        moveNext()
    }

    /**
     * Sets the current card index in the stack.
     * Clears any drag offsets associated with the previous card.
     * If the index is out of bounds, the method does nothing.
     * @param index The index to set as the current card.
     */
    fun setCurrentIndex(index: Int) {
        if (index in 0..<itemCount()) {
            currentCardIndex = index
            dragOffsets.clear()
        }
    }
}
