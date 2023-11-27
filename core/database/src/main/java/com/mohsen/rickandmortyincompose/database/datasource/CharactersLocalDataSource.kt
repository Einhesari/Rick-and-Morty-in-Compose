package com.mohsen.rickandmortyincompose.database.datasource

import com.mohsen.rickandmortyincompose.database.CharactersDao
import com.mohsen.rickandmortyincompose.database.model.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersLocalDataSource @Inject constructor(private val dao: CharactersDao) {

    suspend fun getAllCharacters(count: Int, from: Int) = withContext(Dispatchers.IO) {
        dao.getCharacters(count , from)
    }

    suspend fun getCharacter(id: Int) = withContext(Dispatchers.IO) {
        dao.getCharacter(id)
    }

    suspend fun updateCharacter(character: CharacterEntity) {
        withContext(Dispatchers.IO) {
            dao.upsertCharacter(character.character)
            dao.upsertLocation(character.location)
            dao.upsertOrigin(character.origin)
        }
    }

}