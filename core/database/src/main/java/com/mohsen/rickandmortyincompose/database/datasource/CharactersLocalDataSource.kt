package com.mohsen.rickandmortyincompose.database.datasource

import com.mohsen.rickandmortyincompose.database.CharactersDao
import com.mohsen.rickandmortyincompose.database.model.CharacterEntity
import javax.inject.Inject

class CharactersLocalDataSource @Inject constructor(private val dao: CharactersDao) {

    fun getAllCharacters(count: Int, from: Int) = dao.getCharacters(from, count)

    fun getCharacter(id: Int) = dao.getCharacter(id)

    fun updateCharacter(character: CharacterEntity) {
        dao.upsertCharacter(character.character)
        dao.upsertLocation(character.location)
        dao.upsertOrigin(character.origin)
    }

}