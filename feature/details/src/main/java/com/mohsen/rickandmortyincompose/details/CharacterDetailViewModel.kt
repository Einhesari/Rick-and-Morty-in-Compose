package com.mohsen.rickandmortyincompose.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsen.rickandmortincompose.domain.usecase.CharactersUseCase
import com.mohsen.rickandmortyincompose.model.SitcomCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _event = Channel<CharacterDetailScreenEvents>()
    val event = _event.receiveAsFlow()

    fun getCharacter(characterId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(loading = true, errorText = "", sitcomCharacter = null)
            }
            useCase.getCharacter(characterId)
                .onSuccess {

                }
                .onFailure {

                }
//                .catch {
//                    _state.update {
//                        it.copy(
//                            loading = false,
//                            errorText = "Couldn't find rick or morty :(",
//                            sitcomCharacter = null
//                        )
//                    }
//                    _event.send(
//                        CharacterDetailScreenEvents.ShowError(
//                            it.message!!
//                        )
//                    )
//                }
//                .collect { result ->
//                    _state.update {
//                        it.copy(
//                            loading = false,
//                            errorText = "",
//                            sitcomCharacter = result
//                        )
//                    }
//
//                }
        }
    }
}


data class CharacterDetailScreenState(
    val loading: Boolean,
    val errorText: String,
    val sitcomCharacter: SitcomCharacter?
)

sealed class CharacterDetailScreenEvents {
    data class ShowError(val msg: String) :
        CharacterDetailScreenEvents()
}
