package com.mohsen.rickandmortyincompose.data.repository

import com.mohsen.rickandmortyincompose.data.model.mapToDbModel
import com.mohsen.rickandmortyincompose.database.CharactersDao
import com.mohsen.rickandmortyincompose.database.model.CharacterEntity
import com.mohsen.rickandmortyincompose.database.model.mapToExternalModel
import com.mohsen.rickandmortyincompose.model.SitcomCharacter
import com.mohsen.rickandmortyincompose.network.datasource.CharacterOnlineDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val DB_QUERY_LIMIT = 20

class CharacterRepositoryImpl @Inject constructor(
    private val characterOnlineDataSource: CharacterOnlineDataSource,
    private val charactersDao: CharactersDao
) : CharacterRepository {
    override suspend fun getCharactersByPage(page: Int): Flow<List<SitcomCharacter>> {
        return flow {
            val oldCachedCharacters =
                getCharactersFromDb(page).map { it.mapToExternalModel() }.also {
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
        withContext(Dispatchers.IO) {
            kotlin.runCatching { charactersDao.getCharacter(id).mapToExternalModel() }
        }

    private suspend fun updateDb(characterEntity: CharacterEntity) {
        withContext(Dispatchers.IO) {
            charactersDao.upsertCharacter(characterEntity)
        }
    }

    private suspend fun getCharactersFromDb(page: Int): List<CharacterEntity> {
        return withContext(Dispatchers.IO) {
            charactersDao.getCharacters(
                DB_QUERY_LIMIT, (page - 1) * DB_QUERY_LIMIT
            )
        }
    }
}
