package com.mohsen.rickandmortyincompose.network.datasource

import com.mohsen.rickandmortyincompose.network.datasource.NetworkErrorHandler.apiCall
import com.mohsen.rickandmortyincompose.network.model.CharacterApiResult
import com.mohsen.rickandmortyincompose.network.model.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CharacterDataSource @Inject constructor(private val api: Api) {

    suspend fun getCharactersByPage(page : Int): Flow<CharacterApiResult> = flow {
        emit(apiCall {
            api.getAllCharacters(page)
        })
    }

    suspend fun getCharacter(characterId: Int): Flow<CharacterEntity> = flow {
        emit(apiCall {
            api.getCharacter(characterId)
        })
    }
}