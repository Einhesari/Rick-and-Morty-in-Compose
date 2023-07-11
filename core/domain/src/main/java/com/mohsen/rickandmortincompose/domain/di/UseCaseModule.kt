package com.mohsen.rickandmortincompose.domain.di

import com.mohsen.rickandmortincompose.domain.usecase.CharactersUseCase
import com.mohsen.rickandmortyincompose.data.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    fun provideCharacterUseCase(repo: CharacterRepository): CharactersUseCase {
        return CharactersUseCase(repo)
    }
}