package com.spartapps.swipeable.demo.cards_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spartapps.swipeable.demo.data.CardData
import com.spartapps.swipeable.demo.data.sampleData
import com.spartapps.swipeable.demo.ui.theme.ComposeSwipeableCardsTheme
import com.spartapps.swipeable.demo.utils.rememberDominantColor

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    index: Int,
    cardData: CardData,
    offset: Offset,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(550.dp),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)),
    ) {
        val dominantColor = rememberDominantColor(imageRes = cardData.image)

        Box {
            Image(
                painter = painterResource(id = cardData.image),
                contentDescription = cardData.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                ) {
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, dominantColor)
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(dominantColor)
                    ) {
                        Column(
                            modifier = Modifier.padding(15.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = index.toString(),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = cardData.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = cardData.description,
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                style = MaterialTheme.typography.bodyMedium,
                            )

                            Text(
                                text = offset.toString(),
                                fontSize = 15.sp,
                                color = Color.White,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardItemPreview() {
    ComposeSwipeableCardsTheme {
        CardItem(
            index = 0,
            cardData = sampleData.first(),
            offset = Offset.Zero
        )
    }
}