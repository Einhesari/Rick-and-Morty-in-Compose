package com.mohsen.rickandmortyincompose

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterEntity(
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String?,
    @SerialName("status") val status: String?,
    @SerialName("species") val species: String?,
    @SerialName("type") val type: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("origin") val origin: CharacterOrigin?,
    @SerialName("location") val location: CharacterLocation?,
    @SerialName("image") val image: String?,
    @SerialName("episode") val episodes: ArrayList<String>?,
    @SerialName("url") val url: String?,
    @SerialName("created") val createdDate: String?
)

@Serializable
data class CharacterOrigin(
    @SerialName("name") val name: String?, @SerialName("url") val url: String?
)

@Serializable
data class CharacterLocation(
    @SerialName("name") val name: String?, @SerialName("url") val url: String?
)

fun CharacterEntity.mapToPresentationModel() = Character(
    id = id ?: 0, name = name ?: "", imageUrl = image ?: ""
)