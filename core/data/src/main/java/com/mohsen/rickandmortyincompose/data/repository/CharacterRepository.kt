package com.mohsen.rickandmortyincompose.data.repository

import com.mohsen.rickandmortyincompose.model.Character
import com.mohsen.rickandmortyincompose.model.CharacterScreenModel
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharactersByPage(page: Int): Flow<CharacterScreenModel>
    suspend fun getCharacter(id: Int): Flow<Character>
}