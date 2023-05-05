package com.mohsen.rickandmortyincompose.data.repository

import com.mohsen.rickandmortyincompose.data.model.mapToPresentationModel
import com.mohsen.rickandmortyincompose.model.Character
import com.mohsen.rickandmortyincompose.network.datasource.CharacterDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val characterDataSource: CharacterDataSource) :
    CharacterRepository {
    override suspend fun getCharactersByPage(page: Int): Flow<List<Character>> =
        characterDataSource.getCharactersByPage(page)
            .map { it.results?.map { character -> character.mapToPresentationModel() } ?: listOf() }


    override suspend fun getCharacter(id: Int): Flow<Character> =
        characterDataSource.getCharacter(id).map { it.mapToPresentationModel() }


}
