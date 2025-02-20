package com.spartapps.swipeablecards.ui.lazy

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset

interface LazySwipeableCardsScope<T> {

    fun items(items: List<T>, itemContent: @Composable (T, Offset) -> Unit)
}

class LazySwipeableCardsScopeImpl<T> : LazySwipeableCardsScope<T> {

    private val _items = mutableListOf<LazyCardItemContent<T>>()
    val items: List<LazyCardItemContent<T>> = _items

    override fun items(items: List<T>, itemContent: @Composable (T, Offset) -> Unit) {
        items.forEach {
            _items.add(LazyCardItemContent(it, itemContent))
        }
    }
}
