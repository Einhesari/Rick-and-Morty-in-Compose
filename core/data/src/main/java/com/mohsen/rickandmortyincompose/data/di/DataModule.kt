package com.mohsen.rickandmortyincompose.data.di

import com.mohsen.rickandmortyincompose.data.repository.CharacterRepository
import com.mohsen.rickandmortyincompose.data.repository.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindCharacterRepository(repositoryImpl: CharacterRepositoryImpl): CharacterRepository
}