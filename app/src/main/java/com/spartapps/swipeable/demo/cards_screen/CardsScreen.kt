package com.spartapps.swipeable.demo.cards_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spartapps.swipeable.demo.data.sampleData
import com.spartapps.swipeablecards.state.rememberSwipeableCardsState
import com.spartapps.swipeablecards.ui.SwipeableCards

@Composable
fun CardsScreen(
    modifier: Modifier,
) {
    val state = rememberSwipeableCardsState(
        itemCount = { sampleData.size }
    )

    Column(
        modifier = modifier,
    ) {
        SwipeableCards(
            modifier = Modifier.padding(10.dp),
            items = sampleData,
            state = state,
            onSwipe = { _, _ ->

            }
        ) { item, _ ->
            CardItem(
                cardData = item,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            FloatingActionButton(
                onClick = { state.moveNext() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }

            Text(
                text = "${state.currentCardIndex + 1} of ${sampleData.size}",
            )

            FloatingActionButton(
                onClick = { state.goBack() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null
                )
            }
        }
    }
}
