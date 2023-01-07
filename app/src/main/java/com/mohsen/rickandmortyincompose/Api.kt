package com.mohsen.rickandmortyincompose

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("character/")
    suspend fun getCharacters(@Query("page") page: Int): Character
}