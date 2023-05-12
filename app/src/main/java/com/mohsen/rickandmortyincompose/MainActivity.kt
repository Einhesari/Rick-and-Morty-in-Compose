package com.mohsen.rickandmortyincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mohsen.rickandmortyincompose.designsystem.TopBar
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