package com.mohsen.rickandmortyincompose.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.alpha(0.5f),
            elevation = 4.dp,
            shape = RoundedCornerShape(4.dp),
        ) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Please Wait",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
    }
}
