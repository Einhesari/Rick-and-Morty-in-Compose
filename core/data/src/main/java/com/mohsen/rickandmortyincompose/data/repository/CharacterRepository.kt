package com.mohsen.rickandmortyincompose.data.repository

import com.mohsen.rickandmortyincompose.model.SitcomCharacter
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharactersByPage(page: Int): Flow<List<SitcomCharacter>>
    suspend fun getCharacter(id: Int): Result<SitcomCharacter?>
}