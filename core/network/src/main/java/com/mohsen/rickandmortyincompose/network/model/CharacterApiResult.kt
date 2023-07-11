package com.mohsen.rickandmortyincompose.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterApiResult(
    @SerialName("info") val info: ApiInfo?,
    @SerialName("results") val results: ArrayList<NetworkCharacter>?
)

@Serializable
data class ApiInfo(
    @SerialName("count") val count: Int?,
    @SerialName("pages") val pages: Int?,
    @SerialName("next") val next: String?,
    @SerialName("prev") val prev: String?,
)
