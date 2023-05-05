package com.mohsen.rickandmortyincompose.data.model

import com.mohsen.rickandmortyincompose.model.Character
import com.mohsen.rickandmortyincompose.network.model.CharacterEntity

fun CharacterEntity.mapToPresentationModel() = Character(
    id = id ?: 0,
    name = name ?: "",
    imageUrl = image ?: "",
    origin = origin?.name ?: "",
    location = location?.name ?: "",
    status = status ?: ""
)