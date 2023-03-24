package com.mohsen.rickandmortyincompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
fun CharactersScreen(
    viewModel: CharacterViewModel = hiltViewModel(), onItemClick: (Int) -> Unit
) {
    val viewState by viewModel.state.collectAsState()
    with(viewState) {
        when {
            loading -> Loading()
            errorText.isNotEmpty() -> ErrorView(modifier = Modifier, error = errorText) {
                viewModel.getAllCharacters()
            }
            characters.isNotEmpty() -> CharacterList(characters = characters, onItemClick)
            else -> ErrorView(modifier = Modifier, error = "an Unknown error happened") {
                viewModel.getAllCharacters()
            }
        }
    }
}

@Composable
fun CharacterList(
    characters: List<Character>, onItemClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(characters, key = { character -> character.id }) { character ->
            CharacterItemView(character = character, onItemClick)
        }
    }
}

@Composable
fun ErrorView(modifier: Modifier, error: String, onRetryClicked: () -> Unit) {
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

@Composable
fun CharacterItemView(
    character: Character, onClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick(character.id) },
        elevation = 4.dp,
        shape = RoundedCornerShape(4.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(character.imageUrl)
                    .fallback(R.drawable.placeholder).error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder).crossfade(true).build(),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(16.dp)
                    .clip(CircleShape),
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = character.name,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                textAlign = TextAlign.Center,
            )
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun CharacterPreview() {
    CharacterItemView(
        character = Character(
            1, "Test", "https://i.stack.imgur.com/6Ym15.jpg?s=256&g=1"
        ),
        {}
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
        ),
        {}
    )
}