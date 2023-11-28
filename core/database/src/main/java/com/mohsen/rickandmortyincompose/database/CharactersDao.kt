package com.mohsen.rickandmortyincompose.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mohsen.rickandmortyincompose.database.model.CharacterEntity
import com.mohsen.rickandmortyincompose.database.model.LocationEntity
import com.mohsen.rickandmortyincompose.database.model.OriginEntity
import com.mohsen.rickandmortyincompose.database.model.SimpleCharacterEntity

@Dao
interface CharactersDao {
    @Query("SELECT * FROM character ORDER BY id LIMIT :limit OFFSET :offset")
    fun getCharacters(limit: Int, offset: Int): List<CharacterEntity>

    @Query("SELECT * FROM character WHERE id = :id")
    fun getCharacter(id: Int): CharacterEntity?

    @Upsert(entity = SimpleCharacterEntity::class)
    fun upsertCharacter(character: SimpleCharacterEntity)

    @Upsert(entity = LocationEntity::class)
    fun upsertLocation(location: LocationEntity)

    @Upsert(entity = OriginEntity::class)
    fun upsertOrigin(origin: OriginEntity)
}
