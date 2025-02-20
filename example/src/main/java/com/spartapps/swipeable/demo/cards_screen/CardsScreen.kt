package com.spartapps.swipeable.demo.cards_screen

import android.util.Log
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
import com.spartapps.swipeable.demo.data.largeData
import com.spartapps.swipeablecards.state.rememberSwipeableCardsState
import com.spartapps.swipeablecards.ui.lazy.LazySwipeableCards

@Composable
fun CardsScreen(
    modifier: Modifier,
) {
    val state = rememberSwipeableCardsState(
        itemCount = { largeData.size }
    )

    Column(
        modifier = modifier,
    ) {
        LazySwipeableCards(
            modifier = Modifier.padding(10.dp),
            state = state,
            onSwipe = { item, direction ->
                Log.d("CardsScreen", "onSwipe: $item, $direction")
            },
        ) {
            items(largeData) { item, offset ->
                CardItem(
                    cardData = item,
                    offset = offset
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            FloatingActionButton(
                onClick = { state.moveNext() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                )
            }

            Text(
                text = "${state.currentCardIndex + 1} of ${largeData.size}",
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
