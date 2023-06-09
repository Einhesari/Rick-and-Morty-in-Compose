package com.mohsen.rickandmortyincompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohsen.rickandmortyincompose.database.model.LocationEntity
import com.mohsen.rickandmortyincompose.database.model.OriginEntity
import com.mohsen.rickandmortyincompose.database.model.SimpleCharacterEntity

@Database(
    entities = [SimpleCharacterEntity::class, OriginEntity::class, LocationEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}