package com.saraiva.rick_n_morty.ui.screens.character

import com.saraiva.rick_n_morty.data.model.Character
import com.saraiva.rick_n_morty.data.model.Episode

data class CharacterDetailsState(
    val character: Character?,
    val episodes: List<Episode>,
    val isLoading: Boolean = false,
    val isError: Boolean = false
) {
    companion object {
        fun initial() = CharacterDetailsState(
            character = null,
            episodes = emptyList(),
            isLoading = true,
            isError = false
        )
    }
}

sealed class CharacterDetailsEvents {
    object LoadCharacter : CharacterDetailsEvents()
    data class LoadEpisodes(val character: Character) : CharacterDetailsEvents()
}