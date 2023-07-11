package com.mohsen.rickandmortyincompose.network.datasource

import com.mohsen.rickandmortyincompose.network.datasource.NetworkErrorHandler.safeApiCall
import com.mohsen.rickandmortyincompose.network.model.CharacterApiResult
import com.mohsen.rickandmortyincompose.network.model.NetworkCharacter
import javax.inject.Inject


class CharacterOnlineDataSource @Inject constructor(private val api: Api) {

    suspend fun getCharactersByPage(page: Int): Result<CharacterApiResult> = safeApiCall {
        api.getAllCharacters(page)
    }


    suspend fun getCharacter(characterId: Int): Result<NetworkCharacter> = safeApiCall {
        api.getCharacter(characterId)
    }
}