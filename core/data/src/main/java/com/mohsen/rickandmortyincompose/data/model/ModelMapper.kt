package com.mohsen.rickandmortyincompose.data.model

import com.mohsen.rickandmortyincompose.database.model.CharacterEntity
import com.mohsen.rickandmortyincompose.network.model.NetworkCharacter

fun NetworkCharacter.mapToDbModel() = CharacterEntity(
    id = id ?: -1,
    name = name ?: "",
    status = status ?: "",
    species = species ?: "",
    type = type ?: "",
    gender = gender ?: "",
    imageUrl = image ?: ""
)