package com.spartapps.swipeablecards.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object SwipeableCardsDefaults {
    const val VISIBLE_CARDS_IN_STACK = 3
    const val LOCK_BELOW_CARD_DRAGGING = true
    const val ENABLE_ROTATION = true
    const val DRAGGING_ACCELERATION = 1f
    val STACKED_CARDS_OFFSET = 30.dp
    val SWIPE_THRESHOLD = 100.dp
    val PADDING = 10.dp
}

/**
 * Configuration properties for SwipeableCards component.
 *
 * @property padding Total padding applied to the stack container, scaled by number of visible cards. Defaults to [SwipeableCardsDefaults.PADDING] * visibleCardsInStack.
 * @property swipeThreshold Distance threshold required to trigger a swipe action. Defaults to [SwipeableCardsDefaults.SWIPE_THRESHOLD].
 * @property lockBelowCardDragging When true, prevents cards below the top card from being dragged. Defaults to [SwipeableCardsDefaults.LOCK_BELOW_CARD_DRAGGING].
 * @property enableRotation Enables rotation animation during card swipes. Defaults to [SwipeableCardsDefaults.ENABLE_ROTATION].
 * @property stackedCardsOffset Offset between stacked cards in order to see them. Defaults to [SwipeableCardsDefaults.STACKED_CARDS_OFFSET].
 * @property draggingAcceleration Multiplier for drag gesture sensitivity. Higher values make cards more responsive to drag gestures. Defaults to [SwipeableCardsDefaults.DRAGGING_ACCELERATION].
 */
data class SwipeableCardsProperties(
    val padding: Dp = SwipeableCardsDefaults.PADDING,
    val swipeThreshold: Dp = SwipeableCardsDefaults.SWIPE_THRESHOLD,
    val lockBelowCardDragging: Boolean = SwipeableCardsDefaults.LOCK_BELOW_CARD_DRAGGING,
    val enableRotation: Boolean = SwipeableCardsDefaults.ENABLE_ROTATION,
    val stackedCardsOffset: Dp = SwipeableCardsDefaults.STACKED_CARDS_OFFSET,
    val draggingAcceleration: Float = SwipeableCardsDefaults.DRAGGING_ACCELERATION,
)
