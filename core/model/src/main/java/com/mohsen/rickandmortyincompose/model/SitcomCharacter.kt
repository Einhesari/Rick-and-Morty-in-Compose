package com.mohsen.rickandmortyincompose.model

data class SitcomCharacter(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val origin: Origin,
    val location: Location,
    val status: String
)

data class Origin(val name: String, val url: String)
data class Location(val name: String, val url: String)

