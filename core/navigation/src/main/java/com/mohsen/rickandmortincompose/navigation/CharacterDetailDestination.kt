package com.mohsen.rickandmortincompose.navigation

import android.os.Build
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mohsen.rickandmortyincompose.details.CharacterDetailScreen


object CharacterDetailDestination : Destination {
    override val route: String = "characterDetail"
    const val characterArg = "characterId"
    val routWithArgs = "$route/{$characterArg}"
    val arguments = listOf(navArgument(characterArg) {
        type =
            NavType.IntType
    })
}
fun NavGraphBuilder.characterDetailScreen() {
    composable(
        route = CharacterDetailDestination.routWithArgs,
        arguments = CharacterDetailDestination.arguments
    ) {
        val characterId: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            it.arguments?.getInt(
                CharacterDetailDestination.characterArg
            ) ?: 0
        } else {
            it.arguments?.getInt(CharacterDetailDestination.characterArg) ?: 0
        }
        CharacterDetailScreen(characterId)
    }
}