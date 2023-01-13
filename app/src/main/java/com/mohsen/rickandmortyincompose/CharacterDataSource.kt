package com.mohsen.rickandmortyincompose

import com.mohsen.rickandmortyincompose.NetworkErrorHandler.apiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CharacterDataSource @Inject constructor(private val api: Api) {

    suspend fun getCharacters(): Flow<Character> = flow {
        emit(apiCall {
            api.getCharacters(1)
        })
    }

}