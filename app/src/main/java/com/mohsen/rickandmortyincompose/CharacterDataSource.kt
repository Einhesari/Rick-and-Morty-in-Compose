package com.mohsen.rickandmortyincompose

import javax.inject.Inject


class CharacterDataSource @Inject constructor(private val api: Api) {
    suspend fun getCharacters() {
        api.getCharacters(1)
    }
}