package com.spartapps.swipeablecards.ui.lazy

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.geometry.Offset
import com.spartapps.swipeablecards.state.SwipeableCardsState
import com.spartapps.swipeablecards.ui.SwipeableCard
import com.spartapps.swipeablecards.ui.SwipeableCardDirection
import com.spartapps.swipeablecards.ui.SwipeableCardsFactors
import com.spartapps.swipeablecards.ui.SwipeableCardsProperties
import com.spartapps.swipeablecards.ui.animation.SwipeableCardsAnimations

@Composable
internal fun <T> rememberItemProvider(
    state: SwipeableCardsState,
    properties: SwipeableCardsProperties,
    animations: SwipeableCardsAnimations,
    factors: SwipeableCardsFactors,
    onSwipe: (T, SwipeableCardDirection) -> Unit,
    customLazyListScope: LazySwipeableCardsScope<T>.() -> Unit
): CardItemProvider<T> {
    val customLazyListScopeState = rememberUpdatedState(customLazyListScope)

    return remember {
        CardItemProvider<T>(
            itemsState = derivedStateOf {
                val layoutScope =
                    LazySwipeableCardsScopeImpl<T>().apply(customLazyListScopeState.value)
                layoutScope.items
            },
            state = state,
            properties = properties,
            animations = animations,
            factors = factors,
            onSwipe = onSwipe,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
class CardItemProvider<T>(
    private val itemsState: State<List<LazyCardItemContent<T>>>,
    private val state: SwipeableCardsState,
    private val properties: SwipeableCardsProperties,
    private val animations: SwipeableCardsAnimations,
    private val factors: SwipeableCardsFactors,
    private val onSwipe: (T, SwipeableCardDirection) -> Unit,
) : LazyLayoutItemProvider {

    override val itemCount
        get() = itemsState.value.size

    @Composable
    override fun Item(index: Int, key: Any) {
        val item = itemsState.value.getOrNull(index)
        val scale = factors.scaleFactor(index, state, properties)

        val offset = remember {
            derivedStateOf {
                state.dragOffsets.getOrDefault(
                    key = index,
                    defaultValue = Offset.Zero,
                )
            }
        }

        SwipeableCard(
            onSwipe = { direction ->
                state.moveNext()
                item?.let { cardItem -> onSwipe(cardItem.item, direction) }
            },
            offset = offset,
            properties = properties,
            animations = animations,
            factors = factors,
            draggable = if (properties.lockBelowCardDragging) {
                index == state.currentCardIndex
            } else {
                true
            },
            scale = scale,
            onDragOffsetChange = { offset ->
                state.onDragOffsetChange(
                    index = index,
                    offset = offset,
                )
            },
        ) { offset ->
            item?.itemContent?.invoke(item.item, index, offset)
        }
    }

    fun getItem(index: Int): T? {
        return itemsState.value.getOrNull(index)?.item
    }
}
