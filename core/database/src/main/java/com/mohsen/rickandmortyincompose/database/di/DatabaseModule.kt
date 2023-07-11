package com.mohsen.rickandmortyincompose.database.di

import android.content.Context
import androidx.room.Room
import com.mohsen.rickandmortyincompose.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    ).build()

    @Provides
    fun provideCharactersDao(appDatabase: AppDatabase) = appDatabase.charactersDao()
}