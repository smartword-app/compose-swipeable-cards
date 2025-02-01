package com.spartapps.swipeablecards.ui.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.ui.geometry.Offset

const val CARD_ANIMATION_DAMPING_RATIO = 0.6f
const val CARD_ANIMATION_STIFFNESS = 100f

/**
 * Configuration for SwipeableCards animations including movement and rotation.
 *
 * @property cardsAnimationSpec Spring animation specification for card movement/translation.
 *                             Uses custom damping ratio and stiffness for fluid, natural motion.
 * @property rotationAnimationSpec Spring animation specification for card rotation reset.
 *                                Uses default spring values for natural rotational movement.
 */
data class SwipeableCardsAnimations(
    val cardsAnimationSpec: AnimationSpec<Offset> = spring(
        dampingRatio = CARD_ANIMATION_DAMPING_RATIO,
        stiffness = CARD_ANIMATION_STIFFNESS,
    ),
    val rotationAnimationSpec: AnimationSpec<Float> = spring(),
)
