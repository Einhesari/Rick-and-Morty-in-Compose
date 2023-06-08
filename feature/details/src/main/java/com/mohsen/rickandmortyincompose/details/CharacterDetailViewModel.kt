package com.mohsen.rickandmortyincompose.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsen.rickandmortincompose.domain.usecase.CharactersUseCase
import com.mohsen.rickandmortyincompose.model.SitcomCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val useCase: CharactersUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(
        CharacterDetailScreenState(
            loading = true,
            errorText = "",
            sitcomCharacter = null
        )
    )
    val state = _state.asStateFlow()

    fun getCharacter(characterId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(loading = true, errorText = "", sitcomCharacter = null)
            }
            useCase.getCharacter(characterId)
                .onSuccess { character ->
                    _state.update {
                        it.copy(
                            loading = false,
                            errorText = "",
                            sitcomCharacter = character
                        )
                    }
                }
                .onFailure { throwable ->
                    _state.update {
                        it.copy(
                            loading = false,
                            errorText = "Couldn't find rick or morty :(",
                            sitcomCharacter = null
                        )
                    }
                }
        }
    }
}


data class CharacterDetailScreenState(
    val loading: Boolean,
    val errorText: String,
    val sitcomCharacter: SitcomCharacter?
)
