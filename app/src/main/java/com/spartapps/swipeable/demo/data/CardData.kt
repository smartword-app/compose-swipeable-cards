package com.spartapps.swipeable.demo.data

import androidx.annotation.DrawableRes

data class CardData(
    val id: Int,
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
)
