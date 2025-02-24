package com.spartapps.swipeablecards.utils

import androidx.compose.ui.geometry.Offset

fun Offset.accelerateX(acceleration: Float): Offset {
    return Offset(x = x * acceleration, y = y)
}

fun Offset.consume(other: Offset, reverseX: Boolean = false): Offset {
    val newX = if (reverseX) {
        x - other.x
    } else {
        x + other.x
    }
    return Offset(newX, y + other.y)
}
