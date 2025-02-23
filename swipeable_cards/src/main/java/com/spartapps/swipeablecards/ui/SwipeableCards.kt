package com.spartapps.swipeablecards.ui

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.zIndex
import com.spartapps.swipeablecards.state.SwipeableCardsState
import com.spartapps.swipeablecards.ui.animation.SwipeableCardsAnimations

/**
 * A composable that implements a stack of swipeable cards with Tinder-like interactions.
 *
 * @param T The type of data represented by each card
 * @param modifier Modifier to be applied to the card stack container
 * @param items List of items to be displayed as swipeable cards
 * @param state State holder managing the current cards' position.
 * @param properties Configuration properties for the card stack behavior and appearance
 * @param animations Animation specifications for card movements and transitions
 * @param factors Calculation factors affecting card positioning and animations
 * @param onSwipe Callback invoked when a card is swiped, providing the swiped item and direction
 * @param cardContent Composable content for each card, with access to the item and current offset
 *
 * Example usage:
 * ```
 * SwipeableCards(
 *     items = profileList,
 *     state = rememberSwipeableCardsState(),
 *     onSwipe = { profile, direction ->
 *         // Handle swipe
 *     }
 * ) { profile, offset ->
 *     ProfileCard(profile)
 * }
 * ```
 */
@Deprecated(
    message = "SwipeableCards is deprecated. Use LazySwipeableCards instead for better performance and lazy loading.",
    replaceWith = ReplaceWith(
        expression = "LazySwipeableCards(state, properties, animations, factors, onSwipe) { items(items) { cardContent(it, Offset.Zero) } }",
        imports = ["com.spartapps.swipeablecards.ui.lazy.LazySwipeableCards"]
    )
)
/**
 * @deprecated Use LazySwipeableCards instead for better performance and lazy loading.
 */
@Composable
fun <T> SwipeableCards(
    modifier: Modifier = Modifier,
    items: List<T>,
    state: SwipeableCardsState,
    properties: SwipeableCardsProperties = SwipeableCardsProperties(),
    animations: SwipeableCardsAnimations = SwipeableCardsAnimations(),
    factors: SwipeableCardsFactors = SwipeableCardsFactors(),
    onSwipe: (T, SwipeableCardDirection) -> Unit,
    cardContent: @Composable (T, Offset) -> Unit,
) {
    Box(
        modifier = modifier.padding(
            start = properties.padding,
            bottom = properties.padding.div(2)
        ),
    ) {
        items.forEachIndexed { index, item ->
            val offset by animateOffsetAsState(
                targetValue = factors.cardOffsetCalculation(index, state, properties),
                animationSpec = animations.cardsAnimationSpec,
            )

            val isCardVisible =
                index - state.currentCardIndex in (0..<properties.visibleCardsInStack)
            if (isCardVisible) {
                SwipeableCard(
                    modifier = Modifier
                        .zIndex(-index.toFloat())
                        .offset {
                            offset.round()
                        }
                        .padding(10.dp),
                    onSwipe = {
                        state.moveNext()
                        onSwipe(item, it)
                    },
                    properties = properties,
                    animations = animations,
                    factors = factors,
                    draggable = if (properties.lockBelowCardDragging) {
                        index == state.currentCardIndex
                    } else {
                        true
                    },
                    scale = 1f,
                    onDragOffsetChange = {
                        state.onDragOffsetChange(it)
                    }
                ) { offset ->
                    cardContent(item, offset)
                }
            }
        }
    }
}
