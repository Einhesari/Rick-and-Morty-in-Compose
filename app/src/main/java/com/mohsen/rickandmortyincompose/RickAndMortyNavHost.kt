package com.mohsen.rickandmortyincompose

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mohsen.rickandmortincompose.navigation.CharacterDetailDestination
import com.mohsen.rickandmortincompose.navigation.CharactersDestination
import com.mohsen.rickandmortincompose.navigation.characterDetailScreen
import com.mohsen.rickandmortincompose.navigation.charactersScreen


@Composable
fun RickAndMortyNavHost(
    navController: NavHostController, scaffoldState: ScaffoldState, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = CharactersDestination.route,
        modifier = modifier
    ) {
        charactersScreen(scaffoldState = scaffoldState) { characterId ->
            navController.navigate("${CharacterDetailDestination.route}/$characterId")
        }
        characterDetailScreen()
    }

}