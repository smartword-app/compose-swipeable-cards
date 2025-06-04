package com.spartapps.swipeable.demo.cards_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Reusable action button component with text label
 */
@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    enabled: Boolean = true,
    contentDescription: String? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FloatingActionButton(
            onClick = onClick,
            containerColor = if (enabled) {
                FloatingActionButtonDefaults.containerColor
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            contentColor = if (enabled) {
                contentColorFor(MaterialTheme.colorScheme.surface)
            } else {
                MaterialTheme.colorScheme.onError.copy(alpha = 0.3f)
            },
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            textAlign = TextAlign.Center,
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}