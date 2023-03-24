package com.mohsen.rickandmortyincompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CharacterDetailScreen(characterId: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(4.dp)
        ) {

        }
    }
}