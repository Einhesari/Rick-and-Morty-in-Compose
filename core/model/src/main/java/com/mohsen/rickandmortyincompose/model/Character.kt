package com.mohsen.rickandmortyincompose.model

data class Character(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val origin: String,
    val location: String,
    val status: String
)
