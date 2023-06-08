package com.mohsen.rickandmortincompose.domain.model

import com.mohsen.rickandmortyincompose.model.SitcomCharacter

class CharacterScreenModel(
    val sitcomCharacters: List<SitcomCharacter>,
    val hasNextPage: Boolean
)

