package com.mohsen.rickandmortyincompose.data.repository

import com.mohsen.rickandmortyincompose.data.model.mapToDbModel
import com.mohsen.rickandmortyincompose.database.datasource.CharactersLocalDataSource
import com.mohsen.rickandmortyincompose.database.model.CharacterEntity
import com.mohsen.rickandmortyincompose.database.model.mapToExternalModel
import com.mohsen.rickandmortyincompose.model.SitcomCharacter
import com.mohsen.rickandmortyincompose.network.datasource.CharacterOnlineDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val DB_QUERY_LIMIT = 20

class CharacterRepositoryImpl @Inject constructor(
    private val characterOnlineDataSource: CharacterOnlineDataSource,
    private val charactersLocalDataSource: CharactersLocalDataSource
) : CharacterRepository {
    override suspend fun getCharactersByPage(page: Int): Flow<List<SitcomCharacter>> {
        return flow {
            val oldCachedCharacters =
                getCharactersFromDb(page).map { it.mapToExternalModel() }.also {
                    if (it.isNotEmpty())
                        emit(it)
                }
            characterOnlineDataSource.getCharactersByPage(page).onSuccess { apiResult ->
                apiResult.results?.let { networkResult ->
                    networkResult.forEach { updateDb(it.mapToDbModel()) }
                }

            }.onFailure {
                throw it
            }
            getCharactersFromDb(page).map { it.mapToExternalModel() }.also {
                if (oldCachedCharacters != it) emit(it)
            }
        }
    }

    override suspend fun getCharacter(id: Int): Result<SitcomCharacter> =
        runCatching { charactersLocalDataSource.getCharacter(id).mapToExternalModel() }

    private suspend fun updateDb(characterEntity: CharacterEntity) {
        charactersLocalDataSource.updateCharacter(characterEntity)
    }

    private suspend fun getCharactersFromDb(page: Int): List<CharacterEntity> {
        return charactersLocalDataSource.getAllCharacters(
            DB_QUERY_LIMIT, (page - 1) * DB_QUERY_LIMIT
        )
    }
}
