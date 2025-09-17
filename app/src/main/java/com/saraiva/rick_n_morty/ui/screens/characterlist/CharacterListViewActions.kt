package com.saraiva.rick_n_morty.ui.screens.characterlist

import com.saraiva.rick_n_morty.data.model.Character

data class CharacterListState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean,
    val isPaginating: Boolean,
    val isError: Boolean,
    val hasMore: Boolean
) {
    companion object {
        fun initial() = CharacterListState(
            characters = emptyList(),
            isLoading = true,
            isPaginating = false,
            isError = false,
            hasMore = true
        )
    }
}

sealed class CharacterListEffects {
    data class OpenCharacterDetail(val character: Int) : CharacterListEffects()
}

sealed class CharacterListEvents {
    object LoadCharacters : CharacterListEvents()
    object LoadMoreCharacters : CharacterListEvents()
    object ResetState : CharacterListEvents()
    data class OnCharacterClick(val character: Character) : CharacterListEvents()
}