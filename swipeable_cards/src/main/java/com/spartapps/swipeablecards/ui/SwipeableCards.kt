package com.spartapps.swipeablecards.ui

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
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

@Composable
fun <T> SwipeableCards(
    modifier: Modifier = Modifier,
    items: List<T>,
    state: SwipeableCardsState,
    properties: SwipeableCardsProperties = SwipeableCardsProperties(),
    onSwipe: (T, SwipeableCardDirection) -> Unit,
    animationSpec: AnimationSpec<Offset> = spring(
        dampingRatio = 0.6f,
        stiffness = 100f
    ),
    cardContent: @Composable (T) -> Unit,
) {
    Box(
        modifier = modifier
            .padding(10.dp * properties.visibleCardsInStack)
    ) {
        items.forEachIndexed { index, item ->
            val offset by animateOffsetAsState(
                targetValue = properties.cardOffsetMultiplier(index, state),
                animationSpec = animationSpec,
            )

            if (index in (state.currentCardIndex..<state.currentCardIndex + properties.visibleCardsInStack)) {
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
                    swipeThreshold = properties.swipeThreshold,
                    draggable = if (properties.lockBelowCardDragging) {
                        index == state.currentCardIndex
                    } else {
                        true
                    },
                ) {
                    cardContent(item)
                }
            }
        }
    }
}
