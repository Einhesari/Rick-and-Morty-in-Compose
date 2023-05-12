package com.mohsen.rickandmortincompose.navigation

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohsen.rickandmortyincompose.character.CharactersScreen

object CharactersDestination : Destination {
    override val route: String = "characters"
}

fun NavGraphBuilder.charactersScreen(
    scaffoldState: ScaffoldState,
    navigateTo: (Int) -> Unit
) {
    composable(route = CharactersDestination.route) {
        CharactersScreen(scaffoldState = scaffoldState, onItemClick = navigateTo)
    }
}