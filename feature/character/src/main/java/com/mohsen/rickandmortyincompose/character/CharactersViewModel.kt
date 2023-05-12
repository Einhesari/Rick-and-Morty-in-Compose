package com.mohsen.rickandmortyincompose.character

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
class CharactersViewModel @Inject constructor(private val repository: CharacterRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(
        CharactersScreenState(
            loading = true,
            characters = mutableListOf(),
            errorText = ""
        )
    )
    val state = _state.asStateFlow()

    private val _event = Channel<CharactersScreenEvents>()
    val event = _event.receiveAsFlow()

    init {
        getAllCharacters()
    }

    fun getAllCharacters() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true,
                    errorText = ""
                )
            }
            repository.getCharactersByPage(1)
                .catch {
                    _state.update {
                        _state.value.copy(
                            loading = false,
                            errorText = "Couldn't find rick or morty :("
                        )
                    }
                    _event.send(
                        CharactersScreenEvents.ShowError(
                            it.message!!
                        )
                    )
                }
                .collect { result ->
                    _state.update {
                        it.copy(
                            loading = false,
                            progressBar = false,
                            characters = result,
                            errorText = ""
                        )
                    }
                }
        }
    }
}

data class CharactersScreenState(
    val loading: Boolean,
    val progressBar: Boolean = false,
    val characters: List<Character>,
    val errorText: String
)

sealed class CharactersScreenEvents {
    data class ShowError(val msg: String) :
        CharactersScreenEvents()
}
