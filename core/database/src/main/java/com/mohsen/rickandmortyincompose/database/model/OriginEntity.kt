package com.mohsen.rickandmortyincompose.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohsen.rickandmortyincompose.model.Origin

@Entity(tableName = "origin")
data class OriginEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val url: String
)

fun OriginEntity.mapToExternalModel() = Origin(name = name, url = url)