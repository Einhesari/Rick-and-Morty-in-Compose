package com.mohsen.rickandmortyincompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(val dataSource: CharacterDataSource) : ViewModel() {

    fun getAllCharacters() {
        viewModelScope.launch {
            dataSource.getCharacters()
                .catch {

                }
                .collect {
                }
        }
    }
}

data class CharactersScreenViewState(
    val loading: Boolean,
    val progressBar: Boolean = false,
    val characters: List<>
)