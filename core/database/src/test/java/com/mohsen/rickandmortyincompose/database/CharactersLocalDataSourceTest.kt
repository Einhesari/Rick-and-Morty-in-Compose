package com.mohsen.rickandmortyincompose.database

import com.google.common.truth.Truth.assertThat
import com.mohsen.rickandmortyincompose.database.dao.FakeCharactersDao
import com.mohsen.rickandmortyincompose.database.datasource.CharactersLocalDataSource
import com.mohsen.rickandmortyincompose.database.model.CharacterEntity
import com.mohsen.rickandmortyincompose.database.model.LocationEntity
import com.mohsen.rickandmortyincompose.database.model.OriginEntity
import com.mohsen.rickandmortyincompose.database.model.SimpleCharacterEntity
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class CharactersLocalDataSourceTest {

    private lateinit var sut: CharactersLocalDataSource
    private lateinit var dao: FakeCharactersDao

    @Before
    fun setup() {
        dao = FakeCharactersDao()
        sut = CharactersLocalDataSource(dao)
    }

    @Test
    fun `when getAllCharacters called return list of characters`() = runTest {

        /**
         * Arrange
         */
        val rick = SimpleCharacterEntity(1, "Rick", "Dead", "Human", "", "Male", "URL Of Rick")
        val rickOrigin = OriginEntity(1, "Origin Of Rick", "Origin URL1")
        val rickLocation = LocationEntity(1, "Citadel of Ricks", "Location URL1")
        val rickEntity = CharacterEntity(rick, rickOrigin, rickLocation)

        val morty = SimpleCharacterEntity(2, "Morty", "Alive", "Human", "", "Male", "URL Of Morty")
        val mortyOrigin = OriginEntity(2, "unknown", "Origin URL2")
        val mortyLocation = LocationEntity(2, "Citadel of Ricks", "Location URL2")

        val mortyEntity = CharacterEntity(morty, mortyOrigin, mortyLocation)

        val mockedData = listOf(rickEntity, mortyEntity)

        /**
         * Act
         */
        val characters = sut.getAllCharacters(2, 0)

        /**
         * Assert
         */
        assertThat(characters.size).isEqualTo(mockedData.size)
        assertThat(characters.first()).isEqualTo(mockedData.first())
        assertThat(characters.last()).isEqualTo(mockedData.last())

    }

    @Test
    fun `when getCharacter called return right character`() = runTest {
        /**
         * Arrange
         */
        /**
         * Arrange
         */
        val rick = SimpleCharacterEntity(1, "Rick", "Dead", "Human", "", "Male", "URL Of Rick")
        val rickOrigin = OriginEntity(1, "Origin Of Rick", "Origin URL1")
        val rickLocation = LocationEntity(1, "Citadel of Ricks", "Location URL1")
        val rickEntity = CharacterEntity(rick, rickOrigin, rickLocation)

        /**
         * Act
         */
        /**
         * Act
         */
        val character = sut.getCharacter(1)

        /**
         * Assert
         */

        /**
         * Assert
         */
        assertThat(character).isNotNull()
        assertThat(character?.character?.id).isEqualTo(rickEntity.character.id)
    }

    @Test
    fun `when updateDatabase is called update data if available`() = runTest {
        /**
         * Arrange
         */
        val newRick =
            SimpleCharacterEntity(1, "New Rick", "Dead", "Human", "", "Male", "URL Of Rick")
        val newRickOrigin = OriginEntity(1, "Origin Of Rick", "Origin URL1")
        val newRickLocation = LocationEntity(1, "Citadel of Ricks", "Location URL1")
        val newRickEntity = CharacterEntity(newRick, newRickOrigin, newRickLocation)

        /**
         * Act
         */
        sut.updateCharacter(newRickEntity)

        /**
         * Assert
         */
        val allCharacters = sut.getAllCharacters(2, 0)
        assertThat(allCharacters).hasSize(2)
        assertThat(allCharacters[0].character.name).isEqualTo("New Rick")
    }

    @Test
    fun `when updateDatabase is called insert new data if not available`() = runTest {
        /**
         * Arrange
         */
        val summer =
            SimpleCharacterEntity(3, "Summer", "Alive", "Human", "", "Female", "URL Of Summer")
        val summerOrigin = OriginEntity(3, "Origin Of Summer", "Origin URL3")
        val summerLocation = LocationEntity(3, "Earth", "Location URL3")
        val summerEntity = CharacterEntity(summer, summerOrigin, summerLocation)

        /**
         * Act
         */
        sut.updateCharacter(summerEntity)

        /**
         * Assert
         */
        val allCharacters = sut.getAllCharacters(3, 0)
        assertThat(allCharacters).hasSize(3)
        assertThat(allCharacters[2].character.name).isEqualTo("Summer")
    }

}