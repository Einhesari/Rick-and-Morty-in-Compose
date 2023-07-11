package com.mohsen.rickandmortyincompose.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCharacter(
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String?,
    @SerialName("status") val status: String?,
    @SerialName("species") val species: String?,
    @SerialName("type") val type: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("origin") val origin: NetworkCharacterOrigin?,
    @SerialName("location") val location: NetworkCharacterLocation?,
    @SerialName("image") val image: String?,
    @SerialName("episode") val episodes: ArrayList<String>?,
    @SerialName("url") val url: String?,
    @SerialName("created") val createdDate: String?
)

@Serializable
data class NetworkCharacterOrigin(
    @SerialName("name") val name: String?, @SerialName("url") val url: String?
)

@Serializable
data class NetworkCharacterLocation(
    @SerialName("name") val name: String?, @SerialName("url") val url: String?
)
