package com.spartapps.swipeable.demo.data
import com.spartapps.swipeable.demo.R

val sampleData = listOf(
    CardData(
        id = 1,
        title = "Mountain Sunrise",
        description = "A breathtaking mountain view at sunrise with mist covering the valleys.",
        image = R.drawable.lighthouse
    ),
    CardData(
        id = 2,
        title = "Coastal Sunset",
        description = "A stunning coastal view at sunset with a lighthouse on a rocky cliff.",
        image = R.drawable.home
    ),
    CardData(
        id = 3,
        title = "Desert Twilight",
        description = "A serene desert landscape with towering sand dunes under a twilight sky.",
        image = R.drawable.deseret
    ),
    CardData(
        id = 4,
        title = "Rainforest Waterfall",
        description = "A lush tropical rainforest with a waterfall cascading into a lagoon.",
        image = R.drawable.waterfall
    ),
    CardData(
        id = 5,
        title = "Alpine Lake",
        description = "A picturesque alpine lake surrounded by snow-capped mountains.",
        image = R.drawable.lake
    ),
    CardData(
        id = 6,
        title = "City Skyline at Night",
        description = "A breathtaking city skyline with illuminated skyscrapers reflecting on a river.",
        image = R.drawable.city
    ),
    CardData(
        id = 7,
        title = "Autumn Forest",
        description = "A stunning autumn forest with a winding path covered in golden leaves.",
        image = R.drawable.trees
    )
)

val largeData = (0..<1000).map {
    CardData(
        id = it,
        title = "Mountain Sunrise",
        description = "A breathtaking mountain view at sunrise with mist covering the valleys.",
        image = listOf(
            R.drawable.lighthouse,
            R.drawable.home,
            R.drawable.deseret,
            R.drawable.waterfall,
            R.drawable.lake,
        )[it % 5],
    )
}