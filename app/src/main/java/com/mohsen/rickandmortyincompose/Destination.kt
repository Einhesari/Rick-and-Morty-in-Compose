package com.mohsen.rickandmortyincompose

interface Destination {
    val route: String
}

object CharactersDestination : Destination{
    override val route: String = "characters"
}