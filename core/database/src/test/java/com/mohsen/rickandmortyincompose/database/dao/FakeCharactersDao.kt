package com.mohsen.rickandmortyincompose.database.dao

import com.mohsen.rickandmortyincompose.database.CharactersDao
import com.mohsen.rickandmortyincompose.database.model.CharacterEntity
import com.mohsen.rickandmortyincompose.database.model.LocationEntity
import com.mohsen.rickandmortyincompose.database.model.OriginEntity
import com.mohsen.rickandmortyincompose.database.model.SimpleCharacterEntity

class FakeCharactersDao : CharactersDao {

    private val rick = SimpleCharacterEntity(1, "Rick", "Dead", "Human", "", "Male", "URL Of Rick")
    private val rickOrigin = OriginEntity(1, "Origin Of Rick", "Origin URL1")
    private val rickLocation = LocationEntity(1, "Citadel of Ricks", "Location URL1")

    private val morty =
        SimpleCharacterEntity(2, "Morty", "Alive", "Human", "", "Male", "URL Of Morty")
    private val mortyOrigin = OriginEntity(2, "unknown", "Origin URL2")
    private val mortyLocation = LocationEntity(2, "Citadel of Ricks", "Location URL2")

    private val characters = mutableListOf(rick, morty)
    private val origins = mutableListOf(rickOrigin, mortyOrigin)
    private val locations = mutableListOf(rickLocation, mortyLocation)
    private val entities: List<CharacterEntity>
        get() {
            val entities = mutableListOf<CharacterEntity>()
            characters.forEachIndexed { index, simpleCharacterEntity ->
                entities.add(
                    CharacterEntity(
                        simpleCharacterEntity,
                        origins[index],
                        locations[index]
                    )
                )
            }
            return entities
        }

    override fun getCharacters(limit: Int, offset: Int): List<CharacterEntity> {
        return entities.subList(offset, offset + limit)
    }

    override fun getCharacter(id: Int): CharacterEntity? {
        return (entities.find {
            it.character.id == id
        })
    }

    override fun upsertCharacter(character: SimpleCharacterEntity) {
        characters.indexOfFirst { it.id == character.id }.let {
            if (it >= 0)
                characters[it] = character
            else
                characters.add(character)
        }

    }

    override fun upsertLocation(location: LocationEntity) {
        locations.indexOfFirst { it.id == location.id }.let {
            if (it >= 0)
                locations[it] = location
            else
                locations.add(location)
        }
    }

    override fun upsertOrigin(origin: OriginEntity) {
        origins.indexOfFirst { it.id == origin.id }.let {
            if (it >= 0)
                origins[it] = origin
            else
                origins.add(origin)
        }
    }

}