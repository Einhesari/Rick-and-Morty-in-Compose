package com.mohsen.rickandmortyincompose.database.datasource

import com.mohsen.rickandmortyincompose.database.CharactersDao
import javax.inject.Inject

class CharactersLocalDataSource @Inject constructor(private val dao: CharactersDao) {

    fun getAllCharacters(from: Int, count: Int) = dao.getCharacters(from, count)

}