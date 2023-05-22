package com.mohsen.rickandmortyincompose.character

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mohsen.rickandmortyincompose.common.R
import com.mohsen.rickandmortyincompose.common.network.observeWithLifecycle
import com.mohsen.rickandmortyincompose.designsystem.Error
import com.mohsen.rickandmortyincompose.designsystem.Loading
import com.mohsen.rickandmortyincompose.model.Character
import kotlinx.coroutines.launch

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    onItemClick: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    viewModel.event.observeWithLifecycle {
        when (it) {
            is CharactersScreenEvents.ShowError -> {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(it.msg)
                }
            }
        }
    }

    with(viewState) {
        when {
            loadingDialog -> Loading()
            errorText.isNotEmpty() -> Error(modifier = Modifier, error = errorText) {
                viewModel.getInitialCharacters()
            }
            characters.isNotEmpty() -> {
                Column {
                    if (progressBar) LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp , vertical = 4.dp)
                    )
                    CharacterList(
                        characters = characters, onItemClick, listState
                    )
                }
            }
            else -> Error(modifier = Modifier, error = "an unknown error happened") {
                viewModel.getInitialCharacters()
            }
        }
    }

    val shouldPaginate by remember {
        derivedStateOf {
            (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -10) >= listState.layoutInfo.totalItemsCount - 4
        }
    }

    LaunchedEffect(key1 = shouldPaginate) {
        if (shouldPaginate) viewModel.paginate()
    }
}

@Composable
fun CharacterList(
    characters: List<Character>,
    onItemClick: (Int) -> Unit,
    listState: LazyGridState,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = listState,
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

@OptIn(ExperimentalFoundationApi::class)
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
                    .border(
                        border = BorderStroke(2.dp, MaterialTheme.colors.onBackground), CircleShape
                    )
                    .clip(CircleShape),
            )
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .basicMarquee(),
                text = character.name,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                textAlign = TextAlign.Center,
            )
        }
    }
}
