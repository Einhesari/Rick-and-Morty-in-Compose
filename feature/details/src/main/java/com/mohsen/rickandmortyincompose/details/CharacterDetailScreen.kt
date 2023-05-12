package com.mohsen.rickandmortyincompose.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mohsen.rickandmortyincompose.common.R
import com.mohsen.rickandmortyincompose.designsystem.Error
import com.mohsen.rickandmortyincompose.designsystem.Loading
import com.mohsen.rickandmortyincompose.model.Character

@Composable
fun CharacterDetailScreen(characterId: Int, viewModel: CharacterDetailViewModel = hiltViewModel()) {

    LaunchedEffect(characterId) {
        viewModel.getCharacter(characterId)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    with(state) {
        when {
            loading -> Loading()
            errorText.isNotEmpty() -> Error(modifier = Modifier, error = errorText) {
                viewModel.getCharacter(characterId)
            }
            character != null -> CharacterDetailCard(character)
            else -> Error(modifier = Modifier, error = "an Unknown error happened") {
                viewModel.getCharacter(characterId)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterDetailCard(character: Character) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Card(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 64.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(4.dp),
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        start = 16.dp, top = 64.dp, end = 16.dp, bottom = 32.dp
                    )
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(text = "Name")
                    Text(text = "Origin", modifier = Modifier.padding(top = 16.dp))
                    Text(text = "Location", modifier = Modifier.padding(top = 16.dp))
                    Text(text = "Status", modifier = Modifier.padding(top = 16.dp))
                }
                Column(
                    modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End
                ) {
                    Text(
                        modifier = Modifier.basicMarquee(),
                        text = character.name,
                        style = MaterialTheme.typography.body2,
                        maxLines = 1
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .basicMarquee(),
                        text = character.origin,
                        style = MaterialTheme.typography.body2,
                        maxLines = 1
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .basicMarquee(),
                        text = character.location,
                        style = MaterialTheme.typography.body2,
                        maxLines = 1
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .basicMarquee(),
                        text = character.status,
                        style = MaterialTheme.typography.body2,
                        maxLines = 1
                    )
                }
            }
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(character.imageUrl)
                .fallback(R.drawable.placeholder).error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder).crossfade(true).build(),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(16.dp)
                .border(border = BorderStroke(2.dp, MaterialTheme.colors.onBackground), CircleShape)
                .clip(CircleShape),
        )
    }
}

@Preview
@Composable
fun CharacterDetailCardPreview() {
    CharacterDetailCard(
        character = Character(
            id = 1,
            "Rick",
            "https://i.stack.imgur.com/6Ym15.jpg?s=256&g=1",
            "Iran",
            "Tehran",
            "Alive"
        )
    )
}