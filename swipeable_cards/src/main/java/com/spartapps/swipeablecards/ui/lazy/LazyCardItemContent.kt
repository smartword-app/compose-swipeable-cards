package com.spartapps.swipeablecards.ui.lazy

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset

data class LazyCardItemContent<T> (
    val item: T,
    val itemContent:  @Composable (T, Int, Offset) -> Unit
)
