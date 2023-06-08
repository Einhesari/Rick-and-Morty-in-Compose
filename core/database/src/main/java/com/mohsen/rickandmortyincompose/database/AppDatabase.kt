package com.mohsen.rickandmortyincompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohsen.rickandmortyincompose.database.model.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}