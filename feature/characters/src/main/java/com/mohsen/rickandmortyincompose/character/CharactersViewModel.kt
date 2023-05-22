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
            loadingDialog = true,
            characters = mutableListOf(),
            errorText = "",
            progressBar = false
        )
    )
    val state = _state.asStateFlow()

    private val _event = Channel<CharactersScreenEvents>()
    val event = _event.receiveAsFlow()

    private var nextPage = 1
    private var canPaginate = true

    init {
        getInitialCharacters()
    }

    fun getInitialCharacters() {
        fetchCharacters(1)
    }

    fun paginate() {
        if (!_state.value.loadingDialog)
            fetchCharacters(nextPage)
    }

    private fun fetchCharacters(page: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loadingDialog = page == 1,
                    progressBar = page > 1,
                    errorText = ""
                )
            }
            repository.getCharactersByPage(page)
                .catch {
                    if (page == 1) {
                        _state.update {
                            _state.value.copy(
                                loadingDialog = false,
                                progressBar = false,
                                errorText = "Couldn't find rick or morty :("
                            )
                        }
                    }
                    _event.send(
                        CharactersScreenEvents.ShowError(
                            it.message!!
                        )
                    )
                }
                .collect { result ->
                    _state.update {
                        val characters = mutableListOf<Character>().apply {
                            if (page > 1) {
                                addAll(it.characters + result.characters)
                            } else {
                                addAll(result.characters)
                            }
                        }
                        it.copy(
                            loadingDialog = false,
                            progressBar = false,
                            characters = characters,
                            errorText = ""
                        )
                    }
                    nextPage++
                }
        }
    }
}

data class CharactersScreenState(
    val loadingDialog: Boolean,
    val progressBar: Boolean,
    val characters: List<Character>,
    val errorText: String
)

sealed class CharactersScreenEvents {
    data class ShowError(val msg: String) :
        CharactersScreenEvents()
}
