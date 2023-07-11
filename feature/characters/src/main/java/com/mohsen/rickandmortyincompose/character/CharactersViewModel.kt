package com.mohsen.rickandmortyincompose.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsen.rickandmortincompose.domain.usecase.CharactersUseCase
import com.mohsen.rickandmortyincompose.model.SitcomCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val getCharactersUseCase: CharactersUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(
        CharactersScreenState(
            loadingDialog = true,
            sitcomCharacters = mutableListOf(),
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
        if (!_state.value.loadingDialog) fetchCharacters(nextPage)
    }

    private fun fetchCharacters(page: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loadingDialog = page == 1, progressBar = page > 1, errorText = ""
                )
            }
            getCharactersUseCase.getCharactersByPage(page).onEach { result ->
                _state.update {
                    val sitcomCharacters = mutableListOf<SitcomCharacter>().apply {
                        if (page > 1) {
                            addAll(it.sitcomCharacters + result.sitcomCharacters)
                        } else {
                            addAll(result.sitcomCharacters)
                        }
                    }
                    it.copy(
                        loadingDialog = false,
                        sitcomCharacters = sitcomCharacters,
                    )
                }
                canPaginate = result.hasNextPage
                nextPage++
            }.onCompletion {
                _state.update { it.copy(loadingDialog = false, progressBar = false) }
            }.catch {
                if (_state.value.sitcomCharacters.isEmpty()) {
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
            }.launchIn(viewModelScope)
        }
    }
}

data class CharactersScreenState(
    val loadingDialog: Boolean,
    val progressBar: Boolean,
    val sitcomCharacters: List<SitcomCharacter>,
    val errorText: String
)

sealed class CharactersScreenEvents {
    data class ShowError(val msg: String) : CharactersScreenEvents()
}
