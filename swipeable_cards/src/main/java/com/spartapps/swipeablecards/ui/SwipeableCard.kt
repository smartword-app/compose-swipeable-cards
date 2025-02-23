package com.spartapps.swipeablecards.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.round
import com.spartapps.swipeablecards.ui.animation.SwipeableCardsAnimations

@Composable
internal fun SwipeableCard(
    modifier: Modifier = Modifier,
    properties: SwipeableCardsProperties,
    animations: SwipeableCardsAnimations,
    factors: SwipeableCardsFactors,
    draggable: Boolean,
    scale: Float,
    onDragOffsetChange: (Offset) -> Unit,
    onSwipe: (SwipeableCardDirection) -> Unit,
    content: @Composable (Offset) -> Unit,
) {
    val threshold = with(LocalDensity.current) {
        properties.swipeThreshold.toPx()
    }
    var isDragging by remember { mutableStateOf(false) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val animatedOffset by animateOffsetAsState(
        targetValue = offset,
        animationSpec = animations.cardsAnimationSpec,
    )

    val rotationAnimation by animateFloatAsState(
        targetValue = if (properties.enableRotation && isDragging) {
            factors.rotationFactor(offset)
        } else {
            0f
        },
        animationSpec = animations.rotationAnimationSpec,
    )

    Box(
        modifier = modifier
            .scale(scale)
            .offset {
                if (isDragging) {
                    offset.round()
                } else {
                    animatedOffset.round()
                }
            }
            .rotate(rotationAnimation)
            .then(
                if (draggable) {
                    Modifier.pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                isDragging = true
                            },
                            onDragEnd = {
                                if (offset.x > threshold) {
                                    onSwipe(SwipeableCardDirection.Right)
                                } else if (offset.x < -threshold) {
                                    onSwipe(SwipeableCardDirection.Left)
                                }
                                offset = Offset.Zero
                                onDragOffsetChange(offset)
                                isDragging = false
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                offset += dragAmount.copy(
                                    x = dragAmount.x * properties.draggingAcceleration
                                )
                                onDragOffsetChange(offset)
                            }
                        )
                    }
                } else {
                    Modifier
                }
            ),
    ) {
        content(offset)
    }
}
