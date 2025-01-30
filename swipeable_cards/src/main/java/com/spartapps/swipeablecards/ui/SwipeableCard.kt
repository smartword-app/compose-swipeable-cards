package com.spartapps.swipeablecards.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.round

@Composable
fun SwipeableCard(
    modifier: Modifier = Modifier,
    swipeThreshold: Dp,
    draggable: Boolean,
    onSwipe: (SwipeableCardDirection) -> Unit,
    content: @Composable () -> Unit,
) {
    val threshold = with(LocalDensity.current) { swipeThreshold.toPx() }
    var isDragging by remember { mutableStateOf(false) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    var viewWidth by remember { mutableFloatStateOf(0f) }

    Card(
        modifier = modifier
            .onGloballyPositioned {
                viewWidth = it.size.width.toFloat()
            }
            .offset {
                offset.round()
            }
            .rotate(if (isDragging) offset.x.div(50) else 0f)
            .then(
                if (draggable) {
                    Modifier.pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                isDragging = true
                            },
                            onDragEnd = {
                                if (offset.x > viewWidth / 2 + threshold) {
                                    onSwipe(SwipeableCardDirection.Right)
                                } else if (offset.x < -viewWidth / 2 - threshold) {
                                    onSwipe(SwipeableCardDirection.Left)
                                }
                                offset = Offset.Zero
                                isDragging = false
                            },
                            onDragCancel = {
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                offset += dragAmount.copy(x = dragAmount.x * 1.3f)
                            }
                        )
                    }
                } else {
                    Modifier
                }),
        border = CardDefaults.outlinedCardBorder(),
    ) {
        content()
    }
}
