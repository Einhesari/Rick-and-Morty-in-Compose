package com.mohsen.rickandmortyincompose.database

import com.mohsen.rickandmortyincompose.database.datasource.CharactersLocalDataSource
import org.junit.Before

class CharactersLocalDataSourceTest {

    private lateinit var sut: CharactersLocalDataSource

    @Before
    fun setup() {
        sut = CharactersLocalDataSource()
    }

}