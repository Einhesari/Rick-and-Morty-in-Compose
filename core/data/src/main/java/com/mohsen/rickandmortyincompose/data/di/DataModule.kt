package com.mohsen.rickandmortyincompose.data.di

import com.mohsen.rickandmortyincompose.data.repository.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun provideCharacterRepository(): CharacterRepositoryImpl
}