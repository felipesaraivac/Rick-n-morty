package com.saraiva.rick_n_morty.ui.screens.character

import com.saraiva.rick_n_morty.data.model.Character

sealed class CharacterDetailsEffects {
    data class CharacterLoaded(val character: Character) : CharacterDetailsEffects()
    object Loading : CharacterDetailsEffects()
    object Error : CharacterDetailsEffects()
}

sealed class CharacterDetailsEvents {
    object LoadCharacters : CharacterDetailsEvents()
    data class OnCharacterClick(val character: Character) : CharacterDetailsEvents()
}