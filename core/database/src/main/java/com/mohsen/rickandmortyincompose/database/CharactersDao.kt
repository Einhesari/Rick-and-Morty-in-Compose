package com.mohsen.rickandmortyincompose.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mohsen.rickandmortyincompose.database.model.CharacterEntity

@Dao
interface CharactersDao {
    @Query("SELECT * FROM characterentity ORDER BY id LIMIT :limit OFFSET :offset")
    fun getCharacters(limit: Int, offset: Int): List<CharacterEntity>

    @Query("SELECT * FROM characterentity WHERE id = :id")
    fun getCharacter(id: Int): CharacterEntity

    @Upsert(entity = CharacterEntity::class)
    fun upsertCharacter(characters: CharacterEntity)
}
