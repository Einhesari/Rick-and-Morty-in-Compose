package com.mohsen.rickandmortyincompose

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val imageUrl: String,
)
