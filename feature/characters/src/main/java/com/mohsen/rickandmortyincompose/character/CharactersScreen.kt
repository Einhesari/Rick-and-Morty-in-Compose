package com.mohsen.rickandmortyincompose.character

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mohsen.rickandmortyincompose.common.network.observeWithLifecycle
import com.mohsen.rickandmortyincompose.designsystem.Error
import com.mohsen.rickandmortyincompose.designsystem.Loading
import com.mohsen.rickandmortyincompose.model.SitcomCharacter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CharactersRoute(
    viewModel: CharactersViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val scaffoldState = rememberScaffoldState()
    val shouldPaginate by remember {
        derivedStateOf {
            (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -10) >= listState.layoutInfo.totalItemsCount - 4
        }
    }

    LaunchedEffect(key1 = shouldPaginate) {
        if (shouldPaginate) viewModel.paginate()
    }

    viewModel.event.observeWithLifecycle {
        when (it) {
            is CharactersScreenEvents.ShowError -> {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(it.msg)
                }
            }
        }
    }
    CharacterScreen(viewState, scaffoldState, listState, coroutineScope, onItemClick) {
        viewModel.getInitialCharacters()
    }
}

@Composable
fun CharacterScreen(
    viewState: CharactersScreenState,
    scaffoldState: ScaffoldState,
    listState: LazyGridState,
    coroutineScope: CoroutineScope,
    onItemClick: (Int) -> Unit,
    onRetryClicked: () -> Unit
) {
    val showUpButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 4
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "All Characters") }) },
        scaffoldState = scaffoldState,
        floatingActionButton = {
            AnimatedVisibility(
                visible = showUpButton,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight }
                ),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight * 2 }
                )
            ) {
                FloatingActionButton(backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .navigationBarsPadding(),
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }) {
                    Image(
                        painterResource(id = R.drawable.baseline_keyboard_arrow_up_24),
                        "Navigate to top"
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            with(viewState) {
                if (sitcomCharacters.isNotEmpty()) {
                    val backgroundBlur = if (loadingDialog) 4.dp else 0.dp
                    CharacterList(
                        sitcomCharacters = sitcomCharacters,
                        onItemClick,
                        listState,
                        Modifier.blur(backgroundBlur)
                    )
                }
                if (loadingDialog) Loading()
                if (progressBar) LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 18.dp, vertical = 4.dp)
                )
                if (errorText.isNotEmpty()) Error(
                    modifier = Modifier,
                    error = errorText
                ) {
                    onRetryClicked()
                }
            }
        }
    }
}

@Composable
fun CharacterList(
    sitcomCharacters: List<SitcomCharacter>,
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
        items(sitcomCharacters, key = { character -> character.id }) { character ->
            CharacterItemView(sitcomCharacter = character, onItemClick)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterItemView(
    sitcomCharacter: SitcomCharacter, onClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick(sitcomCharacter.id) },
        elevation = 4.dp,
        shape = RoundedCornerShape(4.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(sitcomCharacter.imageUrl)
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
                text = sitcomCharacter.name,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun CharacterScreenPreview() {

}