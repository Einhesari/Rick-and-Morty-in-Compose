package com.mohsen.rickandmortyincompose.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohsen.rickandmortyincompose.model.Location

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val url: String
)

fun LocationEntity.mapToExternalModel() = Location(name = name, url = url)