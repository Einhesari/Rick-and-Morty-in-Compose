package com.mohsen.rickandmortyincompose

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val dataSource: CharacterDataSource) :
    ViewModel() {

    private val _state = MutableStateFlow(
        CharactersScreenViewState(
            loading = true,
            characters = mutableListOf()
        )
    )
    val state: StateFlow<CharactersScreenViewState>
        get() = _state

    private val _event = MutableSharedFlow<CharacterScreenViewEvents>(replay = 0)
    val event: SharedFlow<CharacterScreenViewEvents>
        get() = _event

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() {
        viewModelScope.launch {
            dataSource.getCharacters()
                .catch {
                    _event.emit(CharacterScreenViewEvents.ShowError(it.message))
                }
                .collect {
                    _state.emit(
                        _state.value.copy(
                            loading = false,
                            progressBar = false,
                            characters = it.results?.map { characterEntity ->
                                characterEntity.mapToPresentationModel()
                            } ?: listOf()
                        )
                    )
                }
        }
    }
}

data class CharactersScreenViewState(
    val loading: Boolean,
    val progressBar: Boolean = false,
    val characters: List<Character>
)

sealed class CharacterScreenViewEvents {
    data class ShowError(val msg: String? = null, @StringRes val msgId: Int? = null) :
        CharacterScreenViewEvents()
}
