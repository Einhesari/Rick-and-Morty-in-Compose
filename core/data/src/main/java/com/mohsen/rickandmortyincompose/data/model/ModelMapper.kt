package com.mohsen.rickandmortyincompose.data.model

import com.mohsen.rickandmortyincompose.database.model.CharacterEntity
import com.mohsen.rickandmortyincompose.database.model.LocationEntity
import com.mohsen.rickandmortyincompose.database.model.OriginEntity
import com.mohsen.rickandmortyincompose.database.model.SimpleCharacterEntity
import com.mohsen.rickandmortyincompose.network.model.NetworkCharacter

fun NetworkCharacter.mapToDbModel() = CharacterEntity(
    character = SimpleCharacterEntity(
        id ?: 0,
        name ?: "",
        status ?: "",
        species ?: "",
        type ?: "",
        gender ?: "",
        image ?: ""
    ),
    location = LocationEntity(id ?: 0, location?.name ?: "", location?.url ?: ""),
    origin = OriginEntity(id ?: 0, origin?.name ?: "", origin?.url ?: "")
)


