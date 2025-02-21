package com.spartapps.swipeablecards.ui.lazy

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.round
import com.spartapps.swipeablecards.state.SwipeableCardsState
import com.spartapps.swipeablecards.ui.SwipeableCardDirection
import com.spartapps.swipeablecards.ui.SwipeableCardsFactors
import com.spartapps.swipeablecards.ui.SwipeableCardsProperties
import com.spartapps.swipeablecards.ui.animation.SwipeableCardsAnimations
import kotlinx.coroutines.launch

/**
 * A composable function that displays a stack of swipeable cards using a lazy approach.
 * The cards are animated and can be swiped in different directions.
 *
 * @param T The type of the data backing the cards.
 * @param modifier The modifier to be applied to the layout.
 * @param state The state of the swipeable cards, which controls the current index and interactions.
 * @param properties Configuration properties for the swipeable cards (e.g., padding, stack behavior).
 * @param animations Animation specifications for swipe transitions.
 * @param factors Calculation factors that determine card positions and offsets.
 * @param onSwipe A callback that is triggered when a card is swiped. Provides the swiped item and direction.
 * @param content A lambda that defines the content of the lazy swipeable cards.
 *
 * Example usage:
 * ```
 * LazySwipeableCards(
 *     state = rememberSwipeableCardsState(),
 *     onSwipe = { profile: Profile, direction ->
 *         // Handle swipe
 *     }
 * ) {
 *     items(profileList) { profile ->
 *         ProfileCard(profile)
 *     }
 * }
 * ```
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> LazySwipeableCards(
    modifier: Modifier = Modifier,
    state: SwipeableCardsState,
    properties: SwipeableCardsProperties = SwipeableCardsProperties(),
    animations: SwipeableCardsAnimations = SwipeableCardsAnimations(),
    factors: SwipeableCardsFactors = SwipeableCardsFactors(),
    onSwipe: (T, SwipeableCardDirection) -> Unit,
    content: LazySwipeableCardsScope<T>.() -> Unit,
) {
    val itemProvider = rememberItemProvider<T>(
        state = state,
        properties = properties,
        animations = animations,
        factors = factors,
        onSwipe = onSwipe,
        customLazyListScope = content,
    )
    val indexes = remember(state.currentCardIndex) {
        itemProvider.getVisibleCardIndexes()
    }

    val animatables = remember {
        mutableStateMapOf<Int, Animatable<Offset, *>>()
    }

    LaunchedEffect(indexes) {
        indexes.forEach { index ->
            animatables.putIfAbsent(index, Animatable(Offset.Zero, Offset.VectorConverter))
        }
    }

    LaunchedEffect(state.currentCardIndex) {
        animatables.forEach { index, animatable ->
            launch {
                animatable.animateTo(
                    targetValue = factors.cardOffsetCalculation(index, state, properties),
                    animationSpec = tween()
                )
            }
        }
    }

    LazyLayout(
        modifier = modifier
            .padding(
                end = properties.padding,
                top = properties.padding.div(2)
            ),
        itemProvider = { itemProvider },
    ) { constraints ->

        val indexesWithPlaceables = indexes.associateWith {
            measure(it, constraints)
        }

        val maxHeight = indexesWithPlaceables.values
            .flatMap { it }
            .maxOfOrNull { it.height } ?: 0

        layout(width = constraints.maxWidth, height = maxHeight) {
            indexesWithPlaceables.forEach { (index, placeables) ->
                val item = itemProvider.getItem(index)
                item?.let {
                    placeables.forEach { placeable ->
                        placeable.placeRelative(
                            position = animatables[index]?.value?.round() ?: IntOffset.Zero,
                            zIndex = -index.toFloat(),
                        )
                    }
                }
            }
        }
    }
}
