package com.spartapps.swipeablecards.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.round
import com.spartapps.swipeablecards.ui.animation.SwipeableCardsAnimations
import com.spartapps.swipeablecards.utils.accelerateX
import com.spartapps.swipeablecards.utils.consume
import kotlin.math.absoluteValue

@Composable
internal fun SwipeableCard(
    modifier: Modifier = Modifier,
    offset: State<Offset>,
    properties: SwipeableCardsProperties,
    animations: SwipeableCardsAnimations,
    factors: SwipeableCardsFactors,
    draggable: Boolean,
    scale: Float,
    onDragOffsetChange: (Offset) -> Unit,
    onSwipe: (SwipeableCardDirection) -> Unit,
    content: @Composable (Offset) -> Unit,
) {
    val internalOffset by offset
    val haptic = LocalHapticFeedback.current
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val threshold = with(LocalDensity.current) {
        properties.swipeThreshold.toPx()
    }
    var isDragging by remember { mutableStateOf(false) }
    var firstHaptic by remember { mutableStateOf(true) }

    val animatedOffset by animateOffsetAsState(
        targetValue = internalOffset,
        animationSpec = animations.cardsAnimationSpec,
    )

    val rotationAnimation by animateFloatAsState(
        targetValue = if (properties.enableRotation) {
            factors.rotationFactor(animatedOffset)
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
                    internalOffset.round()
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
                                if (internalOffset.x > threshold) {
                                    onSwipe(SwipeableCardDirection.Right)
                                } else if (internalOffset.x < -threshold) {
                                    onSwipe(SwipeableCardDirection.Left)
                                }
                                onDragOffsetChange(Offset.Zero)
                                isDragging = false
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                val newOffset = internalOffset.consume(
                                    other = dragAmount.accelerateX(
                                        acceleration = properties.draggingAcceleration,
                                    ),
                                    reverseX = isRtl,
                                )
                                onDragOffsetChange(newOffset)
                                if (properties.enableHapticFeedbackOnThreshold) {
                                    if (internalOffset.x.absoluteValue > threshold) {
                                        if (firstHaptic) {
                                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                            firstHaptic = false
                                        }
                                    } else {
                                        firstHaptic = true
                                    }
                                }
                            }
                        )
                    }
                } else {
                    Modifier
                }
            ),
    ) {
        content(internalOffset)
    }
}
