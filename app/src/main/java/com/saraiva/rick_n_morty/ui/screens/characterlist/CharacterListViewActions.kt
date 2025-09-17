package com.saraiva.rick_n_morty.ui.screens.characterlist

import com.saraiva.rick_n_morty.data.model.Character

sealed class CharacterListEffects {
    data class OpenCharacterDetail(val character: Character) : CharacterListEffects()
    object Loading : CharacterListEffects()
    object Paginating : CharacterListEffects()
    object EndPagination : CharacterListEffects()
    object Error : CharacterListEffects()
}

sealed class CharacterListEvents {
    object LoadCharacters : CharacterListEvents()
    object LoadMoreCharacters : CharacterListEvents()
    data class OnCharacterClick(val character: Character) : CharacterListEvents()
}