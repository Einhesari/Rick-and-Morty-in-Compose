package com.mohsen.rickandmortincompose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohsen.rickandmortyincompose.character.CharactersRoute

object CharactersDestination : Destination {
    override val route: String = "sitcomCharacters"
}

fun NavGraphBuilder.charactersScreen(
    navigateTo: (Int) -> Unit
) {
    composable(route = CharactersDestination.route) {
        CharactersRoute(onItemClick = navigateTo)
    }
}