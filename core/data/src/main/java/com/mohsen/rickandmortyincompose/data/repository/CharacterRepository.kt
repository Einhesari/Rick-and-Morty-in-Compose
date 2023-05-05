package com.mohsen.rickandmortyincompose.data.repository

import com.mohsen.rickandmortyincompose.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharactersByPage(page: Int): Flow<List<Character>>
    suspend fun getCharacter(id: Int): Flow<Character>
}