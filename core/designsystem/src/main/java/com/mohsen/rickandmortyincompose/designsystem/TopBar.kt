package com.mohsen.rickandmortyincompose.designsystem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val BarHeight = 56.dp

@Composable
fun TopBar() {
    Surface(
        modifier = Modifier
            .height(BarHeight)
            .fillMaxWidth(),
        color = MaterialTheme.colors.primary
    ) {
        Row(
            Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Rick And Morty" , style = MaterialTheme.typography.h6)
        }
    }
}