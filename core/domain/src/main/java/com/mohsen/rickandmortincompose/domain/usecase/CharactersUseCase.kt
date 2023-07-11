package com.mohsen.rickandmortincompose.domain.usecase

import com.mohsen.rickandmortincompose.domain.model.CharacterScreenModel
import com.mohsen.rickandmortyincompose.data.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersUseCase @Inject constructor(private val repo: CharacterRepository) {

    suspend fun getCharactersByPage(page: Int) = withContext(Dispatchers.IO) {
        repo.getCharactersByPage(page).map {
            CharacterScreenModel(sitcomCharacters = it, it.size == 20)
        }
    }

    suspend fun getCharacter(id: Int) = withContext(Dispatchers.IO) {
        repo.getCharacter(id)
    }
}