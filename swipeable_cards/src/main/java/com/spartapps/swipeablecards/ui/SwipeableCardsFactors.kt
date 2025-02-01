package com.spartapps.swipeablecards.ui

import androidx.compose.ui.geometry.Offset
import com.spartapps.swipeablecards.state.SwipeableCardsState

/**
 * Configuration for calculation factors that affect card behavior in SwipeableCards.
 *
 * @property rotationFactor Lambda that calculates card rotation angle based on the current offset.
 *                         Default implementation provides natural-feeling rotation during swipes,
 *                         dividing x-offset by 50 to get rotation degrees.
 *
 * @property cardOffsetCalculation Lambda that calculates the position offset for each card in the stack.
 *                                Takes card index, state and properties as parameters.
 *                                Default implementation creates a cascading stack effect by:
 *                                - Calculating vertical and horizontal offset based on card position from top
 *                                - Using stackedCardsOffset property to determine spacing between cards
 *                                - Adjusting offset relative to the currently visible card (currentCardIndex)
 */
data class SwipeableCardsFactors(
    val rotationFactor: (offset: Offset) -> Float = {
        it.x / 50
    },
    val cardOffsetCalculation: (
        index: Int,
        state: SwipeableCardsState,
        props: SwipeableCardsProperties,
    ) -> Offset = { index, state, props ->
        val offset = props.stackedCardsOffset.value * (index - state.currentCardIndex)
        Offset(-offset, offset)
    },
)
