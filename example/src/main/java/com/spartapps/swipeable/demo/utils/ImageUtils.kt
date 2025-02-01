package com.spartapps.swipeable.demo.utils

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.imageResource
import androidx.palette.graphics.Palette

@Composable
fun rememberDominantColor(@DrawableRes imageRes: Int, defaultColor: Int = 0xFFFFFF): Color {
    val bitmap = ImageBitmap.imageResource(imageRes)
    val color = remember(bitmap) {
        Palette.from(bitmap.asAndroidBitmap())
            .generate()
            .getDominantColor(defaultColor)
    }
    return Color(color)
}
