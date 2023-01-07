package com.mohsen.rickandmortyincompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(val dataSource: CharacterDataSource) : ViewModel() {

    fun test() {
        viewModelScope.launch {
            dataSource.getCharacters()
        }
    }
}