package com.mohsen.rickandmortyincompose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun CharactersScreen(viewModel: CharacterViewModel = hiltViewModel()) {
    val viewState by viewModel.state.collectAsState()
    with(viewState) {
        CharacterList(characters = characters)
        if (loading)
            CircularProgressIndicator()
    }
}

@Composable
fun CharacterList(characters: List<Character>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier.padding(16.dp), columns = GridCells.Fixed(2)
    ) {
        items(characters, key = { character -> character.id }) { character ->
            CharacterItemView(character = character)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterItemView(
    character: Character, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(bottom = 16.dp, start = 8.dp, end = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(character.imageUrl)
                    .crossfade(true).build(),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(16.dp)
                    .clip(CircleShape),
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .basicMarquee(),
                text = character.name,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterPreview() {
    CharacterItemView(
        character = Character(
            1, "Test", "https://i.stack.imgur.com/6Ym15.jpg?s=256&g=1"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CharactersListPreview() {
    CharacterList(
        listOf(
            Character(
                1, "Test", "https://i.stack.imgur.com/6Ym15.jpg?s=256&g=1"
            ),
            Character(
                2, "Test2", "https://i.stack.imgur.com/6Ym15.jpg?s=256&g=1"
            ),
            Character(
                3, "Test2", "https://i.stack.imgur.com/6Ym15.jpg?s=256&g=1"
            ),
        )
    )
}