package com.mohsen.rickandmortyincompose.network.datasource

import com.mohsen.rickandmortyincompose.network.model.CharacterApiResult
import com.mohsen.rickandmortyincompose.network.model.CharacterEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("character/")
    suspend fun getAllCharacters(@Query("page") page: Int): Response<CharacterApiResult>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") characterId: Int): Response<CharacterEntity>
}