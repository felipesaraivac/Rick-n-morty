package com.saraiva.rick_n_morty.ui.screens.character

import com.saraiva.rick_n_morty.data.model.Character
import com.saraiva.rick_n_morty.data.model.Episode

data class CharacterDetailsState(
    val character: Character,
    val episode: List<Episode>
)

sealed class CharacterDetailsEffects {
    data class CharacterLoaded(val character: CharacterDetailsState) : CharacterDetailsEffects()
    object Loading : CharacterDetailsEffects()
    object Error : CharacterDetailsEffects()
}

sealed class CharacterDetailsEvents {
    object LoadCharacters : CharacterDetailsEvents()
    data class OnCharacterClick(val character: Character) : CharacterDetailsEvents()
}