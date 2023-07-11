package com.mohsen.rickandmortyincompose.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class SimpleCharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val imageUrl: String
)
