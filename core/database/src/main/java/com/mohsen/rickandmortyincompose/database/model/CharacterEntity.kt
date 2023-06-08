package com.mohsen.rickandmortyincompose.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohsen.rickandmortyincompose.model.SitcomCharacter

@Entity
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val imageUrl: String,
)

fun CharacterEntity.mapToExternalModel() = SitcomCharacter(
    id = id,
    name = name,
    imageUrl = imageUrl,
    origin = "",
    location = "",
    status = status
)