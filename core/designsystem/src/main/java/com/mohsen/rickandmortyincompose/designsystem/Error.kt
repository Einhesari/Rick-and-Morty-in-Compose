package com.mohsen.rickandmortyincompose.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Error(modifier: Modifier, error: String, onRetryClicked: () -> Unit) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.alpha(0.5f),
            elevation = 4.dp,
            shape = RoundedCornerShape(4.dp),
        ) {
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = error,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
                Text(text = "Retry",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.clickable { onRetryClicked() })
            }
        }
    }
}
