package com.mohsen.rickandmortyincompose

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

object CharactersDestination : Destination {
    override val route: String = "characters"

}

object CharacterDetailDestination : Destination {
    override val route: String = "characterDetail"
    const val characterArg = "characterId"
    val routWithArgs = "${route}/{${characterArg}}"
    val arguments = listOf(navArgument(characterArg) {
        type =
            NavType.IntType
    })
}