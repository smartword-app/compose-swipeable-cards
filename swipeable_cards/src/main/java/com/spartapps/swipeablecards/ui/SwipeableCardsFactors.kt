package com.spartapps.swipeablecards.ui

import androidx.compose.ui.geometry.Offset
import com.spartapps.swipeablecards.state.SwipeableCardsState

/**
 * Configuration for calculation factors that affect card behavior in SwipeableCards.
 *
 * @property rotationFactor Lambda that calculates the card rotation angle based on the current offset.
 *                         The default implementation provides a natural-feeling rotation during swipes,
 *                         dividing the x-offset by 50 to determine the rotation in degrees.
 *
 * @property scaleFactor Lambda that calculates the scaling factor for each card in the stack.
 *                       This allows for dynamic resizing of cards based on their index, state, or properties.
 *                       The default implementation keeps all cards at the same scale (1f).
 *
 * @property cardOffsetCalculation Lambda that calculates the position offset for each card in the stack.
 *                                Takes the card index, state, and properties as parameters.
 *                                The default implementation creates a cascading stack effect by:
 *                                - Calculating vertical and horizontal offsets based on the card’s position from the top
 *                                - Using the `stackedCardsOffset` property to determine spacing between cards
 *                                - Adjusting the offset relative to the currently visible card (`currentCardIndex`)
 */
data class SwipeableCardsFactors(
    val rotationFactor: (offset: Offset) -> Float = {
        it.x / 50
    },
    val scaleFactor: (
        index: Int,
        state: SwipeableCardsState,
        props: SwipeableCardsProperties,
    ) -> Float = { index, state, props ->
        1f
    },
    val cardOffsetCalculation: (
        index: Int,
        state: SwipeableCardsState,
        props: SwipeableCardsProperties,
    ) -> Offset = { index, state, props ->
        val offset =
            props.stackedCardsOffset.value * (state.visibleCardsInStack - 1 - (index - state.currentCardIndex))
        Offset(offset, -offset)
    },
)
