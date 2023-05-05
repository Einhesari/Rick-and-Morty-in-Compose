package com.mohsen.rickandmortyincompose.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsen.rickandmortyincompose.data.repository.CharacterRepository
import com.mohsen.rickandmortyincompose.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val repository: CharacterRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(
        CharacterDetailScreenState(
            loading = true,
            errorText = "",
            character = null
        )
    )
    val state = _state.asStateFlow()

    private val _event = Channel<CharacterDetailScreenEvents>()
    val event = _event.receiveAsFlow()

    fun getCharacter(characterId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(loading = true, errorText = "", character = null)
            }
            repository.getCharacter(characterId)
                .catch {
                    _state.update {
                        it.copy(
                            loading = false,
                            errorText = "Couldn't find rick or morty :(",
                            character = null
                        )
                    }
                    _event.send(
                        CharacterDetailScreenEvents.ShowError(
                            it.message!!
                        )
                    )
                }
                .collect { result ->
                    _state.update {
                        it.copy(
                            loading = false,
                            errorText = "",
                            character = result
                        )
                    }

                }
        }
    }
}


data class CharacterDetailScreenState(
    val loading: Boolean,
    val errorText: String,
    val character: Character?
)

sealed class CharacterDetailScreenEvents {
    data class ShowError(val msg: String) :
        CharacterDetailScreenEvents()
}
