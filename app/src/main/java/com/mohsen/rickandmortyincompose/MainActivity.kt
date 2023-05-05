package com.mohsen.rickandmortyincompose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohsen.rickandmortyincompose.designsystem.TopBar
import com.mohsen.rickandmortyincompose.details.CharacterDetailScreen
import com.mohsen.rickandmortyincompose.ui.theme.RickAndMortyInComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyInComposeTheme {
                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Scaffold(modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopBar()
                    }) { innerPadding ->
                    RickAndMortyNavHost(
                        navController = navController,
                        scaffoldState,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RickAndMortyNavHost(
    navController: NavHostController, scaffoldState: ScaffoldState, modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = CharactersDestination.route,
        modifier = modifier
    ) {
        composable(route = CharactersDestination.route) {
            CharactersScreen(scaffoldState = scaffoldState) { characterId ->
                navController.navigate("${CharacterDetailDestination.route}/$characterId")
            }
        }
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

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RickAndMortyInComposeTheme {
        Greeting("Android")
    }
}