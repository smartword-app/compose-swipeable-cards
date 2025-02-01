package com.spartapps.swipeablecards.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * Creates and remembers a [SwipeableCardsState] instance across recompositions.
 *
 * @param initialCardIndex The starting index for the card stack. Defaults to 0 (first card).
 * @param itemCount Lambda that returns the total number of items in the card stack.
 *                 This is provided as a lambda to handle dynamic lists.
 *
 * @return A remembered [SwipeableCardsState] instance that maintains the stack's state.
 *
 * Example usage:
 * ```
 * val state = rememberSwipeableCardsState(
 *     initialCardIndex = 0,
 *     itemCount = { profiles.size }
 * )
 * ```
 */
@Composable
fun rememberSwipeableCardsState(
    initialCardIndex: Int = 0,
    itemCount: () -> Int,
): SwipeableCardsState {
    val state = remember {
        SwipeableCardsState(
            initialCardIndex = initialCardIndex,
            itemCount = itemCount,
        )
    }
    return state
}
