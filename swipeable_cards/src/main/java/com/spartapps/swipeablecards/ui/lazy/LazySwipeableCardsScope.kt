package com.spartapps.swipeablecards.ui.lazy

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset

interface LazySwipeableCardsScope<T> {

    fun addItems(
        items: List<T>,
        itemContent: @Composable (T, Int, Offset) -> Unit,
    )

    fun addItem(
        item: T,
        itemContent: @Composable (T, Int, Offset) -> Unit,
    )
}

class LazySwipeableCardsScopeImpl<T> : LazySwipeableCardsScope<T> {

    private val _items = mutableListOf<LazyCardItemContent<T>>()
    val items: List<LazyCardItemContent<T>> = _items

    override fun addItems(
        items: List<T>,
        itemContent: @Composable (T, Int, Offset) -> Unit,
    ) {
        items.forEach {
            _items.add(LazyCardItemContent(it, itemContent))
        }
    }

    override fun addItem(
        item: T,
        itemContent: @Composable (T, Int, Offset) -> Unit,
    ) {
        _items.add(LazyCardItemContent(item, itemContent))
    }
}

inline fun <reified T> LazySwipeableCardsScope<T>.items(
    items: List<T>,
    noinline itemContent: @Composable (T, Int, Offset) -> Unit
) = addItems(items, itemContent)

inline fun <reified T> LazySwipeableCardsScope<T>.item(
    item: T,
    noinline itemContent: @Composable (T, Int, Offset) -> Unit
) = addItem(item, itemContent)
