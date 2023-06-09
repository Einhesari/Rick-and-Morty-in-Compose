package com.mohsen.rickandmortyincompose.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.mohsen.rickandmortyincompose.model.SitcomCharacter

data class CharacterEntity(
    @Embedded
    val character: SimpleCharacterEntity,
    @Relation(parentColumn = "id", entityColumn = "id")
    val origin: OriginEntity,
    @Relation(parentColumn = "id", entityColumn = "id")
    val location: LocationEntity
)

fun CharacterEntity.mapToExternalModel() = SitcomCharacter(
    id = character.id,
    name = character.name,
    imageUrl = character.imageUrl,
    origin = origin.mapToExternalModel(),
    location = location.mapToExternalModel(),
    status = character.status
)